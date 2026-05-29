package com.example.board.service;

import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    // 게시글 전체 조회
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글 단건 조회
    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));
        post.increaseViewCount();
        return new PostResponseDto(post);
    }

    // 게시글 전체 조회 (페이징)
    public Page<PostResponseDto> getAllPosts(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostResponseDto::new);
    }

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), requestDto.getAuthor());
        return new PostResponseDto(postRepository.save(post));
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("본인이 작성한 글만 수정할 수 있습니다.");
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("본인이 작성한 글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}