/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.base.mapper;

import java.util.List;

/**
 * @Author：pbh
 * @Date：2018-11-09 14:18
 * @Description：pojoDAO支持类实现
 */
public interface BaseMapper<T> {

    /**
     * 插入数据
     *
     * @param entity 需要插入的对象
     * @return 插入的记录条数
     */
    int insert(T entity);

    /**
     * 批量插入
     *
     * @param list 插入的列表
     * @return count 插入的条数
     */
    int insertBatch(List<T> list);

    /**
     * 删除数据
     *
     * @param id 删除的记录的id
     * @return int 删除的条数
     */
    int delete(String id);

    /**
     * 批量删除
     *
     * @param ids 所有需要删除的记录的id
     * @return int 删除的条数
     */
    int deleteBatch(String[] ids);

    /**
     * 逻辑删除（更新is_delete字段为1）
     *
     * @param id 需要逻辑删除的字段
     * @return 删除的条数
     */
    int deleteLogic(String id);

    /**
     * 更新数据
     *
     * @param entity 更新的记录的数据
     * @return 更新的条数
     */
    int update(T entity);

    /**
     * 批量更新
     *
     * @param list 批量更新的记录
     * @return 更新的记录条数
     */
    int updateBatch(List<T> list);

    /**
     * 查询所有数据列表
     *
     * @return 所有表中的数据
     */
    List<T> findAll();

    /**
     * 查询数据列表，如果需要分页,请设置分页对象,
     *
     * <pre>
     *     model.setPage(new Page<T>());
     *
     *     findList(model);//分页查询
     * </pre>
     *
     * @param model 查询列表的参数
     * @return 符合条件的列表数据
     */
    List<T> findList(T model);

    /**
     * 查询数据记录
     *
     * @param model 查询参数
     * @return 符合条件的记录条数
     */
    int getCount(T model);

    /**
     * 获取单条数据
     *
     * @param id 查询的id
     * @return 查询到的数据
     */
    T get(String id);

}