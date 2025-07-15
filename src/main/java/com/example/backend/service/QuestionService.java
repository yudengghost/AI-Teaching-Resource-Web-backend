package com.example.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Question;

public interface QuestionService {

    /**
     * 
     * @param question
     * @return 是否添加成功
     */
    boolean addQuestion(Question question);

    /**
     * 
     * @param current 当前页
     * @param size 一页大小
     * @return page
     */
    Page<Question> getHistory(int current, int size, String username, String type, String level, String subject);

    /**
     * 
     * @param id
     * @return 是否删除成功
     */
    boolean deleteQuestion(Long id);

    /**
     * 
     * @param question
     * @return 是否更新成功
     */
    boolean updateQuestion(Question question);
}
