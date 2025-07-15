package com.example.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;

@Component
public class Administrator {
    
    @Autowired
    private UserMapper userMapper;
    
    public boolean isAdministrator(String username){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if(user != null){
            if(user.getRole() == 1){
                return true;
            }
        }
        return false;
    }

}
