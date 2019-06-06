package com.base.service;

import com.base.pojo.Page;

import java.util.List;

/**
 * @Author：pbh
 * @Date：2018-11-09 14:18
 * @Description：Service基类
 */
public interface BaseService<T> {

    /**
     * 插入数据
     *
     * @param entity
     */
    void insert(T entity);

    /**
     * 批量插入
     *
     * @param list
     */
    void insertBatch(List<T> list);

    /**
     * 删除数据
     *
     * @param id
     */
    void delete(String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteBatch(String[] ids);

    /**
     * 逻辑删除
     *
     * @param id
     */
    void deleteLogic(String id);

    /**
     * 更新数据
     *
     * @param entity
     */
    void update(T entity);

    /**
     * 批量更新
     *
     * @param list
     */
    void updateBatch(List<T> list);

    /**
     * 查询所有数据列表
     *
     * @param
     * @return List
     */
    List<T> findAll();

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：model.setPage(new Page<T>());
     *
     * @param model
     * @return List
     */
    List<T> findList(T model);

    /**
     * 查询数据记录
     *
     * @param model
     * @return int
     */
    int getCount(T model);

    /**
     * 获取单条数据
     *
     * @param id
     * @return T
     */
    T get(String id);

    /**
     * 保存数据
     *
     * @param entity
     */
    void save(T entity);

    /**
     * 查询分页数据
     *
     * @param entity
     * @return Page
     */
    Page<T> findPage(T entity);

}
