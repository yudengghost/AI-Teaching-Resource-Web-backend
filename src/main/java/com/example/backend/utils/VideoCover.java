package com.example.backend.utils;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class VideoCover {
    
    @Value("${alapi.token:}")
    private String token; // 在application.properties中配置alapi.token

    private String videoTitle;
    
    /**
     * 根据视频链接获取视频封面图片文件
     * 
     * @param videoUrl 视频URL
     * @return 封面图片的二进制数据，如获取失败返回null
     */
    public byte[] getVideoCover(String videoUrl) {
        if (StringUtils.isEmpty(videoUrl)) {
            return null;
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
                
                // 检查API响应是否成功
                if (root.get("code").asInt() == 200 && root.has("data") && root.get("data").has("cover")) {
                    // 获取封面URL
                    String coverUrl = root.get("data").get("cover").asText();

                    // 获取视频标题
                    videoTitle = root.get("data").get("title").asText();
                    
                    // 获取图片二进制数据
                    return downloadImage(coverUrl);
                }
            }
        } catch (Exception e) {
            System.err.println("获取视频封面失败: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * 下载图片并返回二进制数据
     * 
     * @param imageUrl 图片URL
     * @return 图片的二进制数据
     */
    private byte[] downloadImage(String imageUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // 发送GET请求并获取二进制响应
            ResponseEntity<byte[]> response = restTemplate.exchange(
                imageUrl, 
                HttpMethod.GET, 
                entity, 
                byte[].class
            );
            
            return response.getBody();
        } catch (Exception e) {
            System.err.println("下载图片失败: " + e.getMessage());
            return null;
        }
    }

    public String getVideoTitle() {
        return videoTitle;
    }
}
