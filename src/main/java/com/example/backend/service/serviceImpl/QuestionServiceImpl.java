package com.example.backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Question;
import com.example.backend.mapper.QuestionMapper;
import com.example.backend.service.QuestionService;
import com.example.backend.utils.Administrator;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private Administrator administrator;

    @Override
    public boolean addQuestion(Question question) {
        return questionMapper.insert(question) > 0;
    }

    @Override
    public Page<Question> getHistory(int current, int size, String username, String type, String level, String subject) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if(!administrator.isAdministrator(username)){
            queryWrapper.eq("username", username);
        }
        if(type != null){
            queryWrapper.eq("type", type);
        }
        if(level != null){
            queryWrapper.eq("level", level);
        }
        if(subject != null){
            queryWrapper.eq("subject", subject);
        }
        return questionMapper.selectPage(new Page<>(current, size), queryWrapper);
    }

    @Override
    public boolean deleteQuestion(Long id) {
        return questionMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateQuestion(Question question) {
        return questionMapper.updateById(question) > 0;
    }
}
