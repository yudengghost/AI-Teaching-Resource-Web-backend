package com.example.backend.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("video")
@Data
public class Video {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String username;
    private String url;
    private String coverUrl;
}
