package com.example.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.entity.Ppt;
import com.example.backend.service.PptService;

@RestController
@RequestMapping("/ppt")
public class PptController {

    @Autowired
    PptService pptService;
    
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestBody Ppt ppt){
        Map<String, Object> response = new HashMap<>();
        
        if(pptService.Upload(ppt)){
            response.put("code", 0);
            response.put("message", "上传成功");
            return ResponseEntity.ok(response);
        }
        response.put("code", -1);
        response.put("message", "上传失败");
        response.put("ppt",ppt);
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("history")
    public IPage<Ppt> getHistoryRecords(
        @RequestParam(defaultValue = "1") int current,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam("username") String username) {
        
        return pptService.getHistory(current, size, username);
    }
}
