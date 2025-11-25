package com.example.board;

import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }

    // 샘플 데이터 생성
    @Bean
    public CommandLineRunner initData(PostRepository postRepository) {
        return args -> {
            postRepository.save(new Post("Spring Boot 시작하기", "Spring Boot 기본 개념을 배워봅시다"));
            postRepository.save(new Post("JPA 개념 정리", "JPA는 Java Persistence API의 약자입니다"));
            postRepository.save(new Post("Spring Data JPA 알아보기", "Repository 인터페이스만으로 CRUD 구현"));
            postRepository.save(new Post("Hibernate와 JPA", "Hibernate는 JPA의 구현체입니다"));
            postRepository.save(new Post("데이터베이스 설계", "효율적인 테이블 구조 만들기"));
            postRepository.save(new Post("RESTful API 개발", "REST 원칙을 따르는 API 설계"));

            System.out.println("샘플 데이터 " + postRepository.count() + "개 생성 완료");
        };
    }
}