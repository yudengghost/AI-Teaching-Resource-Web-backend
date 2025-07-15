package com.example.backend.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;

@Component
public class GlobalConfig {

    @Bean
    public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List ddlLrist) {
        return new DdlApplicationRunner(ddlLrist);
    }

}
