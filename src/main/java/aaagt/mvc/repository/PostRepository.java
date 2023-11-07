package aaagt.mvc.repository;

import aaagt.mvc.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> all();

    Post getById(long id);

    Post save(Post post);

    void removeById(long id);
}
