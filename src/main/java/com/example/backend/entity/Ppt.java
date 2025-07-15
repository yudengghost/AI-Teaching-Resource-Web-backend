package com.example.backend.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@TableName("ppt")
@Data
public class Ppt {

    @TableId(type = IdType.INPUT)
    private Long id;
    private String subject;
    private String coverUrl;
    private Long templateId;
    private String username;
    @JsonFormat(locale="zh_CN",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String outline;
    private String category;
    private String style;
    private String themeColor;
    private Long userId;
}
