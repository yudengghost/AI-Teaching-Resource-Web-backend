package com.example.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Ppt;

public interface PptService {

    /**
     * 
     * @param ppt
     * @return 是否上传成功
     */
    boolean Upload(Ppt ppt);
    
    /**
     * 
     * @param current 当前页
     * @param size 一页大小
     * @return page
     */
    Page<Ppt> getHistory(int current, int size, String username);
}
