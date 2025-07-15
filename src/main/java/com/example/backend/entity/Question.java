package com.example.backend.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@TableName("question")
@Data
public class Question {
    @TableId(type = IdType.AUTO)
    public Long id;
    public String subject;
    public String type;
    public String level;
    public String content;
    @JsonFormat(locale="zh_CN", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    public Date createTime;
    public String username;
}
