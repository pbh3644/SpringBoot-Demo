package com.base.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.util.SecurityUtil;
import com.util.Snowflake;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import static com.util.NumberUtil.TEN;


/**
 * @Author：pbh
 * @Date：2018-11-09 14:20
 * @Description：pojo基类
 */
@Data
public abstract class BaseEntity<T> implements Serializable {


    /**
     * 没删除
     */
    public final static String NOT_DELETE = "N";

    /**
     * 删除了
     */
    public final static String DELETE = "Y";

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    protected String id;

    /**
     * 创建人ID
     */
    protected String addUserId;

    /**
     * 增加时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    protected Date addTime;


    /**
     * 修改人ID
     */
    protected String updateUserId;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    protected Date updateTime;

    /**
     * 删除人ID
     */
    protected String deleteUserId;

    /**
     * 删除时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    protected Date deleteTime;

    /**
     * 状态N:正常,Y:删除
     */
    protected String deleteFlag;

    /**
     * 备注
     */
    protected String remark;


    /**
     * 当前实体分页对象
     */
    protected Page<T> page;

    /**
     * 开始日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    protected Date beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    protected Date finishDate;

    /**
     * 是否是新记录（默认：false），调用setNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     *
     * @return
     */
    @JsonIgnore
    protected boolean newRecord;

    /**
     * 空构造函数
     */
    public BaseEntity() {
        super();
    }

    /**
     * 构造函数
     *
     * @param id
     */
    public BaseEntity(final String id) {
        super();
        this.id = StringUtils.isEmpty(id) ? snowflakeId() : id;
    }


    /**
     * 是否是新记录（默认：false），调用setNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     *
     * @return
     */
    @JsonIgnore
    public boolean newIsRecord() {
        return newRecord || idIsBlank();
    }

    /**
     * 判断uuid是否为空
     *
     * @return
     */
    private boolean idIsBlank() {
        return StringUtils.isBlank(id);
    }

    /**
     * 是否是新记录（默认：false），调用setNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    public void setNewRecord(final boolean newRecord) {
        this.newRecord = newRecord;
    }

    /**
     * 保存数据库前预处理
     * 设置当前时间和当前操作人
     */
    public void preInsert() {
        this.id = StringUtils.isBlank(id) ? snowflakeId() : this.id;
        this.setAddTime(currentTime());
        this.setAddUserId(SecurityUtil.getUserId());
        this.setDeleteFlag(NOT_DELETE);
    }

    /**
     * 保存数据库前预更新
     */
    public void preUpdate() {
        this.setUpdateTime(currentTime());
        this.setUpdateUserId(SecurityUtil.getUserId());
    }

    /**
     * 删除数据库前预删除
     */
    public void preDelete() {
        this.setDeleteFlag(DELETE);
        this.setDeleteTime(currentTime());
        this.setDeleteUserId(SecurityUtil.getUserId());
    }

    /**
     * 根据雪花算法获取ID数字生成
     **/
    public String snowflakeId() {
        int i = new Random().nextInt(TEN);
        int j = new Random().nextInt(TEN);
        Snowflake snowflake = new Snowflake(i, j);
        return snowflake.getNextKey();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public Date currentTime() {
        return new Date();
    }
}