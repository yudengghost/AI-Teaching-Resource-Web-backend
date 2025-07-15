package com.example.backend.service.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.utils.Administrator;
import com.example.backend.mapper.VideoMapper;
import com.example.backend.service.VideoService;
import com.example.backend.entity.Video;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private Administrator administrator;

    @Override
    public boolean uploadVideo(Video video) {
        return videoMapper.insert(video) > 0;
    }

    @Override
    public Page<Video> getVideoList(int current, int size, String username, String keyword) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        if(!administrator.isAdministrator(username)){
            queryWrapper.eq("username", username);
        }
        if(keyword != null){
            queryWrapper.like("title", keyword);
        }
        return videoMapper.selectPage(new Page<>(current, size), queryWrapper);
    }

    @Override
    public boolean deleteVideo(String videoId) {
        return videoMapper.deleteById(videoId) > 0;
    }
}
