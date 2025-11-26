package com.example.board.service;

import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post not found"));

        // readOnly = false
        // 1. 엔티티 조회
        // 2. 스냅샷 저장
        // 3. 트랜잭션이 끝날 때 비교
        // 4. 변경이 있으면 update

        // readOnly = true
        // 1. 엔티티 조회
    }

    public List<Post> getAllPosts() {

//        return postRepository.findAll(
//                Sort.by(Sort.Direction.DESC, "id")
//        );
        return postRepository.findAllByOrderByIdDesc();
    }

    @Transactional
    public Post updatePost(Long id, Post updatePost) {
        Post post = getPostById(id);
        post.setTitle(updatePost.getTitle());
        post.setContent(updatePost.getContent());
        return post;
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public void testFirstLevelCache() {
        Post post1 = postRepository.findById(1L)
                .orElseThrow();
        System.out.println(post1.getTitle());

        Post post2 = postRepository.findById(2L)
                .orElseThrow();
        System.out.println(post2.getTitle());

        System.out.println(post1 == post2);

    }

    @Transactional
    public void testWriteBehind() {
        Post post = postRepository.findById(1L)
                .orElseThrow();

        post.setTitle("hello!!!!");
        System.out.println("update1");

        post.setTitle("hi!!!!!!!");
        System.out.println("update2");

        post.setTitle("bi!!!!");
        System.out.println("update3");

        System.out.println("method end");
    }

    @Transactional
    public void testDirtyChecking() {
        Post post = postRepository.findById(1L)
                .orElseThrow();
        System.out.println("SELECT!!!!!");

    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }

    public List<Post> searchPostsByTitleOrContent(String keyword) {
//        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword); // 검색하려고 하는 단어는 하나라서 매개변수는 한개지만 제목, 내용에서 찾는거라 함수에 키워드 2개 전달
//        return postRepository.searchByKeyword(keyword);
        return postRepository.searchByTitleNative(keyword);
    }

    public List<Post> getRecentPosts() {
//        return postRepository.findTop3ByOrderByCreatedAtDesc();
//        return postRepository.findRecentPostsNative();
        return postRepository.findRecentPosts(PageRequest.of(0, 3));
    }

    public Page<Post> getPostPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // 게시물 자동 대량 게시
    @Transactional
    public void createDummyPosts(int count) {
        for(int i = 1; i <= count; i++) {
            Post post = new Post(i + "번 제목", "게시물내용");
            postRepository.save(post);
        }
    }

    public Page<Post> searchPostsPage(String keyword, Pageable pageable) {
        return postRepository.findByTitleContaining(keyword, pageable);
    }

    public Slice<Post> getPostSlice(Pageable pageable) {
        return postRepository.findAllBy(pageable);
    }

    public List<Post> getAllPostsWithFetchJoin() {
//        return postRepository.findAll();
        return postRepository.findAllWithComments();
    }

    public List<Post> getAllPostsWithEntityGraph() {
        return postRepository.findAllWithCommentsEntityGraph();
    }
}
