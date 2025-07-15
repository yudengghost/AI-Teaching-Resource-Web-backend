package com.example.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user){
        Map<String, Object> response = new HashMap<>();

        if(userService.isUsernameTaken(user.getUsername())){
            response.put("code", -1);
            response.put("message", "用户名已存在");
            return ResponseEntity.badRequest().body(response);
        }
        if(userService.register(user)){
            response.put("code", 0);
            response.put("message", "注册成功");
            return ResponseEntity.ok(response);
        }
        response.put("code", -1);
        response.put("message", "注册失败");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user){
        Map<String, Object> response = new HashMap<>();

        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if(loginUser != null){
            Map<String, Object> data = new HashMap<>();
            data.put("id", loginUser.getId());
            data.put("username", loginUser.getUsername());
            data.put("nickname", loginUser.getNickname());
            data.put("gender", loginUser.getGender());
            data.put("phone", loginUser.getPhone());
            data.put("birthday", loginUser.getBirthday());
            data.put("role", loginUser.getRole());

            response.put("code", 0);
            response.put("message", "登录成功");
            response.put("data", data);
            return ResponseEntity.ok(response);
        }
        response.put("code", -1);
        response.put("message", "登录失败");
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestParam("id") long id, @RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        user.setId(id);

        if(userService.updateUser(user)){
            response.put("code", 0);
            response.put("message", "更新成功");
            return ResponseEntity.ok(response);
        }
        response.put("code", -1);
        response.put("message", "更新失败");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/getUser")
    public ResponseEntity<Map<String, Object>> getUser(@RequestParam("id") long id){
        Map<String, Object> response = new HashMap<>();

        User user = userService.getUser(id);
        if(user != null){
            Map<String, Object> data = new HashMap<>();
            data.put("id", user.getId());
            data.put("username", user.getUsername());
            data.put("nickname", user.getNickname());
            data.put("gender", user.getGender());
            data.put("phone", user.getPhone());
            data.put("birthday", user.getBirthday());
            data.put("role", user.getRole());

            response.put("code", 0);
            response.put("message", "获取用户信息成功");
            response.put("data", data);
            return ResponseEntity.ok(response);
        }
        response.put("code", -1);
        response.put("message", "获取用户信息失败");
        return ResponseEntity.badRequest().body(response);
    }
}
