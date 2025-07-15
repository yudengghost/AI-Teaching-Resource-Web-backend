package com.example.backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user){
        try {
            int result = userMapper.insert(user);
            return result > 0;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean isUsernameTaken(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public User login(String username, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return null;
        }
        else if(user.getPassword().equals(password)){
            return user;
        }
        else{
            return null;
        }
    }

    @Override
    public boolean updateUser(User user){
        try {
            // 检查用户名是否已存在（排除当前用户）
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername()).ne("id", user.getId());

            if(userMapper.selectCount(queryWrapper) > 0) {
                return false;
            }

            int result = userMapper.updateById(user);
            return result > 0;
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("更新用户信息失败原因: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(long id){
        return userMapper.selectById(id);
    }
}
