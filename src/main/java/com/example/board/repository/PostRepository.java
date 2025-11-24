package com.example.board.repository;

import com.example.board.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        String jpql = "SELECT p FROM Post p";
        return em.createQuery(jpql, Post.class).getResultList();
    }

    public Post update(Post post) {
        return em.merge(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public List<Post> findByTitleContaining(String keyword) {
        String jpql = "SELECT p FROM Post p WHERE p.title LIKE :keyword";
        return em.createQuery(jpql, Post.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    // 1. 비영속 (id가 부여되지 않음)
    // new Post("title", "content")

    // 2. 영속 (id가 부여됨)
    // em.persist(post);

    // => detach(), clear()

    // 3. 준영속 (detached 수정하는중)
    // em.detach(post)

    // 4. 삭제
    // em.remove(post)
}
