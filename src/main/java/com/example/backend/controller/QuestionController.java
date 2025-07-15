package com.example.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Question;
import com.example.backend.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addQuestion(@RequestBody Question question){
        Map<String, Object> result = new HashMap<>();
        
        boolean isSuccess = questionService.addQuestion(question);
        if(isSuccess){
            result.put("code", 0);
            result.put("message", "添加成功");
            return ResponseEntity.ok(result);
        }
        result.put("code", -1);
        result.put("message", "添加失败");
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getHistory(
            @RequestParam("current") int current, 
            @RequestParam("size") int size, 
            @RequestParam("username") String username,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "subject", required = false) String subject){
        Map<String, Object> result = new HashMap<>();

        Page<Question> page = questionService.getHistory(current, size, username, type, level, subject);
        result.put("code", 0);
        result.put("message", "获取成功");
        result.put("data", page);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteQuestion(@RequestParam("id") Long id){
        Map<String, Object> result = new HashMap<>();
        boolean isSuccess = questionService.deleteQuestion(id);
        if(isSuccess){
            result.put("code", 0);
            result.put("message", "删除成功");
            return ResponseEntity.ok(result);
        }
        result.put("code", -1);
        result.put("message", "删除失败");
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateQuestion(@RequestBody Question question){
        Map<String, Object> result = new HashMap<>();
        boolean isSuccess = questionService.updateQuestion(question);
        if(isSuccess){
            result.put("code", 0);
            result.put("message", "更新成功");
            return ResponseEntity.ok(result);
        }
        result.put("code", -1);
        result.put("message", "更新失败");
        return ResponseEntity.badRequest().body(result);
    }
}
