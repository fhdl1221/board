package com.example.board.repository;

import java.util.List;
import com.example.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    // 기본 CRUD 메서드

    // 저장 (INSERT of UPDATE)
    // Post save(Post entity)

    // 조회
    // Optional<Post> findById(Long id);
    // List<Post> findAll();
    // List<Post> findAll(Sort sort);

    // 삭제
    // void deleteById(Long id);
    // void delete(Post entity);

    // 개수 조회
    // long count();

    // 존재 여부 확인
    // boolean existsById(Long id)

    List<Post> findByTitleContaining(String keyword);
}
