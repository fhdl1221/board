package com.example.board.repository;

import com.example.board.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;

    // @RequiredArgsConstructor가 아래의 코드 자동 생성해줌
//    public PostRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    private final RowMapper<PostDto> rowMapper = (rs, rowNum) -> {
        return new  PostDto(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    };

    public List<PostDto> findAll() {
        String sql = "SELECT * FROM post";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public PostDto findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        // queryForObject => 단일 행 조회
        PostDto post =  jdbcTemplate.queryForObject(sql, rowMapper, id);
        return post;
    }

    public void save(PostDto postDto) {
        String sql = "INSERT INTO post (title, content) VALUES (?, ?)";
        jdbcTemplate.update(sql, postDto.getTitle(), postDto.getContent());
    }

    public void update(Long id, PostDto postDto) {
        String sql = "UPDATE post SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, postDto.getTitle(), postDto.getContent(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
