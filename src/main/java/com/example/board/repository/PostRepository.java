package com.example.board.repository;

import java.util.List;
import com.example.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // findBy + 필드명 + 조건

    // LIKE %keyword%
    List<Post> findByTitleContaining(String keyword);

    // @Query 방식
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> searchByTitle(@Param("keyword") String keyword);

    // LIKE keyword%
    List<Post> findByTitleStartingWith(String keyword);

    // >
    List<Post> findByIdGreaterThan(Long id);

    // ORDER BY id DESC
    List<Post> findALlByOrderByIdDesc();

    // 제목 or 내용으로 검색
    List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

    // 제목 or 내용으로 검색 @Query
    @Query("""
        SELECT p FROM Post p 
        WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%
        ORDER BY p.createdAt DESC 
    """)
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    @Query(value = """
        SELECT * FROM post 
        WHERE title LIKE %:keyword% 
        ORDER BY id DESC
    """, nativeQuery = true)
    List<Post> searchByTitleNative(@Param("keyword") String keyword);

    // 최근 게시물 3개 출력
    List<Post> findTop3ByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findRecentPosts(Pageable pageable);

    @Query(value = "SELECT * FROM post ORDER BY created_at DESC LIMIT 3", nativeQuery = true)
    List<Post> findRecentPostsNative();

    // List<Post> findAll() => JpaRepository가 구현해준 메서드 오버로딩
    Page<Post> findAll(Pageable pageable);
}
