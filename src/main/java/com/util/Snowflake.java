package com.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * description 雪花算法生成数据库主键。实现主键全局唯一，其中希望在spring的启动阶段实现一个雪花算法的对象创建，其中工作ID和数据中心ID读取配置文件中配置的值。
 *
 * @author zhanghuangze
 * @date 2018-07-24 15:31
 * <p>
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间戳(毫秒级)，注意，41位时间戳不是存储当前时间的时间戳，而是存储时间戳的差值（当前时间戳 - 开始时间戳)
 * 得到的值），这里的的开始时间戳，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间戳，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位dataCenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间戳)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 **/
@Slf4j
public class Snowflake {
    /**
     * 起始的时间戳,2018-07-24 03:36:37
     */
    private final static long START_TIME_MILLIS = 1532417796523L;
    /**
     * 机器id所占的位数
     */
    private final static long MACHINE_BIT = 5L;

    /**
     * 序列号占用的位数
     */
    private static final long SEQUENCE_BIT = 12L;

    /**
     * 数据中心占用的位数
     */
    private final static long DATA_CENTER_BIT = 5L;

    /**
     * 支持的最大数据标识id，结果是31
     */
    public final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    public final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 机器ID向左移12位
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    /**
     * 数据标识id向左移17位(12+5)
     */
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    /**
     * 时间戳向左移22位(5+5+12)
     */
    private final static long TIME_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /**
     * 工作机器ID(0~31)
     */
    private long machineId;

    /**
     * 数据中心ID(0~31),目前只有一个数据中心，所以只有一个值
     */
    private long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private static long sequence = 0L;

    /**
     * 上次生成ID的时间戳
     */
    private static long lastTimeMillis = -1L;

    /**
     * @param machineId    机器ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public Snowflake(long machineId, long dataCenterId) {
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("配置文件有问题，导致了机器码" + machineId + "不在0~31范围内");
        }
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("配置文件有问题，导致了数据中心码" + dataCenterId + "不在0~31范围内");
        }
        this.machineId = machineId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @return 当前时间戳
     */
    private static long getNextTimeMillis() {
        long nextMillis = getTimeMillis();
        while (nextMillis <= lastTimeMillis) {
            nextMillis = getTimeMillis();
        }
        return nextMillis;
    }

    /**
     * 产生下一个Id，此处用于生产主键Id，方法已经是线程安全的了
     * 测试结果，生成一百万个键耗时1.025秒
     *
     * @return SnowflakeId
     */
    public synchronized String getNextKey() {
        long nowTimeMillis = getTimeMillis();
        if (nowTimeMillis < lastTimeMillis) {
            throw new RuntimeException("系统时钟回退过，导致了当前时间小于过去时间");
        } else if (nowTimeMillis == lastTimeMillis) {
            //相同毫秒内，序列号自增，&运算的目的是实现循环，也就是第4096个会变成0
            sequence = (++sequence) & MAX_SEQUENCE;
            //毫秒内序列溢出，循环一圈后，sequence才会等于0，说明已经是第4096个数据了。
            if (sequence == 0L) {
                //阻塞到下一个毫秒,获得新的时间戳
                nowTimeMillis = getNextTimeMillis();
            }
        } else {
            //时间戳改变，毫秒内序列重置，序列号置为0会导致雪花算法的都是生成偶数，后续分表会有问题，所以采用生成0,1随机数的方式。
            sequence = ThreadLocalRandom.current().nextLong(0L, 2L);
        }
        //上次生成ID的时间戳
        lastTimeMillis = nowTimeMillis;
        //移位并通过或运算拼到一起组成64位的ID=1+41+5+5+12
        //时间戳部分移位22+数据中心码移位17+机器码移位12+序列号部分
        long result = (nowTimeMillis - START_TIME_MILLIS) << TIME_LEFT | dataCenterId << DATA_CENTER_LEFT | machineId << MACHINE_LEFT | sequence;
        return String.valueOf(result);
    }
}
