package com.example.backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Ppt;
import com.example.backend.mapper.PptMapper;
import com.example.backend.service.PptService;
import com.example.backend.utils.Administrator;

@Service
public class PptServiceImpl implements PptService{
    @Autowired
    private PptMapper pptMapper;

    @Autowired
    private Administrator administrator;

    @Override
    public boolean Upload(Ppt ppt){
        try {
            int result = pptMapper.insert(ppt);
            return result > 0;
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("上传失败原因: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Page<Ppt> getHistory(int current, int size, String username){
        try{
            Page<Ppt> page = new Page<>(current, size);
            QueryWrapper<Ppt> queryWrapper = new QueryWrapper<>();
            if(!administrator.isAdministrator(username)){
                queryWrapper.eq("username", username);
            }
            return pptMapper.selectPage(page, queryWrapper);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("获取历史记录失败原因: " + e.getMessage());
            return null;
        }
    }
}
