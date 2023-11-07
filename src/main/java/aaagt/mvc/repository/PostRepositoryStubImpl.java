package aaagt.mvc.repository;

import aaagt.mvc.exception.NotFoundException;
import aaagt.mvc.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private static final AtomicLong idCounter = new AtomicLong(0L);
    private static final Map<Long, Post> storage = new ConcurrentHashMap<>();

    public List<Post> all() {
        return storage.values().stream()
                .filter(post -> !post.getRemoved())
                .toList();
    }

    public Post getById(long id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException();
        }
        final var post = storage.get(id);
        if (post.getRemoved()) {
            throw new NotFoundException();
        }
        return post;
    }

    public Post save(Post post) {
        final long id;
        if (post.getId() == 0) {
            id = idCounter.incrementAndGet();
            post.setId(id);
        } else {
            id = post.getId();
            if (!storage.containsKey(id)) {
                throw new NotFoundException();
            }
            if (storage.get(id).getRemoved()) {
                throw new NotFoundException();
            }
        }
        storage.put(id, post);
        return post;
    }

    public void removeById(long id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException();
        }
        final var post = storage.get(id);
        if (post.getRemoved()) {
            throw new NotFoundException();
        }
        post.setRemoved(true);
    }
}
