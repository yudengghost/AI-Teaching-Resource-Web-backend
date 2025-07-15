package com.example.backend.service;

import com.example.backend.entity.User;

public interface UserService {
    
    /**
     * 用户注册
     * 
     * @param user 用户信息
     * @return 注册是否成功
     */
    boolean register(User user);

    /**
     * 
     * @param username 用户名
     * @return 是否被占用
     */
    boolean isUsernameTaken(String username);

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    User login(String username, String password);

    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     * @return 更新是否成功
     */
    boolean updateUser(User user);

    /**
     * 获取用户信息
     * 
     * @param id 用户id
     * @return 用户信息
     */
    User getUser(long id);

}
