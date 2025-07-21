package com.example.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VideoCover {
    
    @Value("${alapi.token:}")
    private String token; // 在application.properties中配置alapi.token

    private final static Logger logger = LoggerFactory.getLogger(VideoCover.class);
    
    /**
     * 根据视频链接获取视频封面链接
     * 
     * @param videoUrl 视频URL
     * @return 封面URL，如无法解析则返回默认封面
     */
    public String getVideoCover(String videoUrl) {
        if (StringUtils.isEmpty(videoUrl)) {
            return "/default-video-cover.jpg";
        }
        
        try {
            // 哔哩哔哩视频
            if (videoUrl.contains("bilibili.com")) {
                // 使用第三方API获取B站视频封面 - POST请求
                RestTemplate restTemplate = new RestTemplate();
                String apiUrl = "https://v3.alapi.cn/api/bilibili/cover";
                
                // 设置请求头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                
                // 构建请求体JSON
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode requestBody = mapper.createObjectNode();
                if (!StringUtils.isEmpty(token)) {
                    requestBody.put("token", token);
                }
                requestBody.put("c", videoUrl);
                
                // 创建HTTP请求实体
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
                
                // 发送POST请求
                String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);
                
                // 解析JSON响应
                JsonNode root = mapper.readTree(response);
                // logger.info("code: " + root.get("code").asInt());
                
                // 检查API响应是否成功
                if (root.get("code").asInt() == 200) {
                    return root.get("data").get("cover").asText();
                }
                
                
                return null;
            } 
            // 优酷视频
            else if (videoUrl.contains("youku.com")) {
                Pattern pattern = Pattern.compile("id_(\\w+)");
                Matcher matcher = pattern.matcher(videoUrl);
                if (matcher.find()) {
                    String videoId = matcher.group(1);
                    return "http://r1.ykimg.com/0541040" + videoId;
                }
            }
            // 腾讯视频
            else if (videoUrl.contains("v.qq.com")) {
                Pattern pattern = Pattern.compile("/(\\w+)\\.html");
                Matcher matcher = pattern.matcher(videoUrl);
                if (matcher.find()) {
                    String videoId = matcher.group(1);
                    return "https://puui.qpic.cn/vpic_cover/" + videoId + "/0";
                }
            }
            // YouTube
            else if (videoUrl.contains("youtube.com") || videoUrl.contains("youtu.be")) {
                Pattern pattern = Pattern.compile("v=([\\w-]+)|youtu\\.be/([\\w-]+)");
                Matcher matcher = pattern.matcher(videoUrl);
                if (matcher.find()) {
                    String videoId = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
                    return "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";
                }
            }
        } catch (Exception e) {
            System.err.println("获取视频封面失败: " + e.getMessage());
        }
        
        return null;
    }
    
}
