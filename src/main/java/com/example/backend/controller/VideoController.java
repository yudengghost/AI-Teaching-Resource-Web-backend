package com.example.backend.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Video;
import com.example.backend.service.VideoService;
import com.example.backend.utils.VideoCover;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoCover videoCover;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadVideo(@RequestBody Video video) {
        Map<String, Object> result = new HashMap<>();
        if(videoService.uploadVideo(video)){
            result.put("code", 0);
            result.put("message", "上传成功");
            return ResponseEntity.ok(result);
        }
        result.put("code", -1);
        result.put("message", "上传失败");
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/getVideoList")
    public ResponseEntity<Map<String, Object>> getVideoList(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam("username") String username, @RequestParam("keyword") String keyword) {
        Map<String, Object> result = new HashMap<>();
        Page<Video> videoPage = videoService.getVideoList(current, size, username, keyword);
        result.put("code", 0);
        result.put("message", "获取视频列表成功");
        result.put("data", videoPage);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteVideo(@RequestParam("id") String id) {
        Map<String, Object> result = new HashMap<>();
        if(videoService.deleteVideo(id)){
            result.put("code", 0);
            result.put("message", "删除成功");
            return ResponseEntity.ok(result);
        }
        result.put("code", -1);
        result.put("message", "删除失败");
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/getVideoCover")
    public ResponseEntity<Map<String, Object>> getVideoCover(@RequestParam("videoUrl") String videoUrl) {
        Map<String, Object> result = new HashMap<>();
        byte[] cover = videoCover.getVideoCover(videoUrl);
        String title = videoCover.getVideoTitle();
        if(cover == null){
            result.put("code", -1);
            result.put("message", "获取视频封面失败");
            return ResponseEntity.badRequest().body(result);
        }
        result.put("code", 0);
        result.put("message", "获取视频封面成功");
        result.put("data", Base64.getEncoder().encodeToString(cover));
        result.put("title", title);
        return ResponseEntity.ok(result);
    }
}
