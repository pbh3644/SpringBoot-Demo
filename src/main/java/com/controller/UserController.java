package com.controller;

import com.base.pojo.Page;
import com.entity.User;
import com.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pangbohuan
 * @description 用户管理控制层
 * @date 2019-06-06 12:07
 **/
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询所有用户列表
     */
    @GetMapping("find_all")
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 根据条件查询分页
     */
    @PostMapping("find_page")
    public Page<User> findPage(@RequestBody User user) {
        return userService.findPage(user);
    }

    /**
     * 增加用户
     */
    @PutMapping("add")
    public String add(@RequestBody User user) {
        userService.insert(user);
        return "增加用户成功";
    }

    /**
     * 批量增加用户
     */
    @PutMapping("add_batch")
    public String addBatch(@RequestBody List<User> list) {
        userService.insertBatch(list);
        return "批量增加用户成功";
    }

    /**
     * 修改用户
     */
    @PostMapping("change")
    public String change(@RequestBody User user) {
        userService.update(user);
        return "修改用户成功";
    }

    /**
     * 批量修改用户
     */
    @PostMapping("change_batch")
    public String changeBatch(@RequestBody List<User> list) {
        userService.updateBatch(list);
        return "批量修改用户成功";
    }

    /**
     * 物理删除用户
     */
    @DeleteMapping("delete")
    public String delete(String id) {
        userService.delete(id);
        return "删除用户成功";
    }

    /**
     * 逻辑删除用户
     */
    @DeleteMapping("delete_logic")
    public String deleteLogic(String id) {
        userService.deleteLogic(id);
        return "删除用户成功";
    }

    /**
     * 批量逻辑删除用户
     */
    @DeleteMapping("delete_batch")
    public String deleteBatch(String[] ids) {
        userService.deleteBatch(ids);
        return "删除用户成功";
    }

    /**
     * 根据ID获取用户
     */
    @GetMapping("get")
    public User get(String id) {
        return userService.get(id);
    }
}
