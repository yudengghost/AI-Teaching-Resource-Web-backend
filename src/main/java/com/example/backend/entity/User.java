package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;
@TableName("user")
@Data
public class User {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String nickname;
    private String gender;
    private String phone;
    private String password;
    @JsonFormat(locale="zh_CN",timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birthday;
    private Integer role;
}
