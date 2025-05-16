package com.example.feedback4.Config;

import com.example.feedback4.service.StudentServiceImp; // Updated import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public StudentService studentService() {
        return new StudentServiceImp();
    }
}