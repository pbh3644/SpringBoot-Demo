package com.entity;

import com.base.pojo.BaseEntity;
import lombok.Data;

/**
 * @author pangbohuan
 * @description 用户表实体类
 * @date 2019-06-06 11:49
 **/
@Data
public class User extends BaseEntity<User> {

    private String id;

    private String name;
}
