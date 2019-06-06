package com.base.service.impl;

import com.base.mapper.BaseMapper;
import com.base.pojo.BaseEntity;
import com.base.pojo.Page;
import com.base.service.BaseService;
import com.util.ErrorInfoConstants;
import com.util.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.util.CompareSceneException.customNumericalEquality;
import static com.util.CompareSceneException.customStringIsNull;
import static com.util.NumberUtil.ZERO;


/**
 * @Author：pbh
 * @Date：2018-11-09 14:18
 * @Description：ServiceImpl基类-批量操作设置了事务,单项操作没设置
 */
public class BaseServiceImpl<D extends BaseMapper<T>, T extends Serializable> implements BaseService<T> {


    /**
     * 数字 ，0
     */
    public static final int INT_ZERO = 0;

    /**
     * 一次批量操作的数据量
     */
    public static final int BATCH_OPERATION_COUNT = 50;

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    @Override
    public T get(String id) {
        checkoutId(id);
        return dao.get(id);
    }

    @Override
    public List<T> findList(T model) {
        return dao.findList(model);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public int getCount(T model) {
        return dao.getCount(model);
    }

    @Override
    public Page<T> findPage(T entity) {
        Page<T> page = ((BaseEntity) entity).getPage();
        if (page == null) {
            page = new Page<T>();
            ((BaseEntity) entity).setPage(page);
        }
        page.setRows(dao.findList(entity));
        page.setCount(getCount(entity));
        return page;
    }

    /***************************** 增删改操作 *****************************/
    @Override
    public void insert(T entity) {
        ((BaseEntity) entity).preInsert();
        checkoutResult(dao.insert(entity), ErrorInfoConstants.ADD_ERROR);
    }

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public void insertBatch(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //批量提交的子列表
        List<T> subList = new ArrayList<T>();
        for (final T t : list) {
            ((BaseEntity) t).preInsert();
            subList.add(t);
            //子列表已满,批量提交
            if (subList.size() == BATCH_OPERATION_COUNT) {
                checkoutResult(dao.insertBatch(subList), ErrorInfoConstants.BATCH_ADD_ERROR);
                subList = new ArrayList<T>();
            }
        }
        //子列表未满的部分,做一次批量提交
        if (subList.size() > INT_ZERO && subList.size() < BATCH_OPERATION_COUNT) {
            checkoutResult(dao.insertBatch(subList), ErrorInfoConstants.BATCH_ADD_ERROR);
        }
    }

    @Override
    public void save(T entity) {
        int result;
        BaseEntity baseEntity = ((BaseEntity) entity);
        if (baseEntity.newIsRecord()) {
            baseEntity.preInsert();
            result = dao.insert(entity);
        } else {
            checkoutId(baseEntity.getId());
            ((BaseEntity) entity).preUpdate();
            result = dao.update(entity);
        }
        checkoutResult(result, ErrorInfoConstants.SAVE_ERROR);
    }

    @Override
    public void update(T entity) {
        BaseEntity baseEntity = ((BaseEntity) entity);
        checkoutId(baseEntity.getId());
        baseEntity.preUpdate();
        checkoutResult(dao.update(entity), ErrorInfoConstants.UPDATE_ERROR);
    }

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public void updateBatch(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //批量提交的子列表
        List<T> subList = new ArrayList<T>();
        for (final T t : list) {
            ((BaseEntity) t).preUpdate();
            subList.add(t);
            //子列表已满,批量提交
            if (subList.size() == BATCH_OPERATION_COUNT) {
                checkoutResult(dao.updateBatch(subList), ErrorInfoConstants.BATCH_UPDATE_ERROR);
                subList = new ArrayList<T>();
            }
        }
        //子列表未满的部分,做一次批量提交
        if (subList.size() > ZERO && subList.size() < BATCH_OPERATION_COUNT) {
            checkoutResult(dao.updateBatch(subList), ErrorInfoConstants.BATCH_UPDATE_ERROR);
        }
    }

    @Override
    public void delete(final String id) {
        checkoutId(id);
        checkoutResult(dao.delete(id), ErrorInfoConstants.DELETE_ERROR + id);
    }

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public void deleteBatch(final String[] ids) {
        for (String id : ids) {
            checkoutId(id);
        }
        checkoutResult(dao.deleteBatch(ids), ErrorInfoConstants.BATCH_DELETE_ERROR + Arrays.toString(ids));
    }

    @Override
    public void deleteLogic(final String id) {
        checkoutId(id);
        checkoutResult(dao.deleteLogic(id), ErrorInfoConstants.DELETE_ERROR + id);
    }

    /**
     * 判断ID的合法性
     * 不合法就抛异常
     */
    protected static void checkoutId(String id) {
        customStringIsNull(id, ErrorInfoConstants.ID_NOT_NULL);
    }

    /**
     * 判断操作数据库返回的行数结果
     * 等于0就抛异常
     */
    protected static void checkoutResult(long result, String err) {
        customNumericalEquality(INT_ZERO, result, err);
    }
}
