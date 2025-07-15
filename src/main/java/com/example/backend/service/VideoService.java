package com.example.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Video;

public interface VideoService {

    /**
     * 上传视频
     * 
     * @param video 视频信息
     * @return 上传是否成功
     */
    boolean uploadVideo(Video video);

    /**
     * 获取视频列表
     * @param current 当前页
     * @param size 每页大小
     * @return 视频列表
     */
    Page<Video> getVideoList(int current, int size, String username, String keyword);

    /**
     * 删除视频
     * @param videoId 视频ID
     * @return 删除是否成功
     */
    boolean deleteVideo(String videoId);

}
