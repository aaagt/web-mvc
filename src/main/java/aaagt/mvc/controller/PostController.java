package aaagt.mvc.controller;

import aaagt.mvc.dto.PostDto;
import aaagt.mvc.model.Post;
import aaagt.mvc.service.PostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    private static Post postFromRequestDto(PostDto requestDto) {
        return new Post(requestDto.getId(), requestDto.getContent());
    }

    private static PostDto postToResponseDto(Post post) {
        return new PostDto(post.getId(), post.getContent());
    }

    @GetMapping
    public List<PostDto> all() {
        return service.all().stream()
                .map(PostController::postToResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getById(@PathVariable long id) {
        final var responsePost = service.getById(id);

        return postToResponseDto(responsePost);
    }

    @PostMapping
    public PostDto save(@RequestBody PostDto requestDto) {
        final var requestPost = postFromRequestDto(requestDto);
        final var responsePost = service.save(requestPost);
        return postToResponseDto(responsePost);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
