package ru.netology.repository;

import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private ConcurrentHashMap<Long, Post> listPost;

    public List<Post> all() {
        return listPost.values().stream().toList();
    }

    public Optional<Post> getById(long id) {
        if (listPost.containsKey(id)) {
            return Optional.of(listPost.get(id));
        } else {
            return Optional.empty();
        }
    }

    public Post save(Post post) {
        long id = post.getId();
        AtomicLong newId = new AtomicLong(id + 1);
        String content = post.getContent();

        if (id == 0) {
            post.setId(newId.intValue());
            listPost.put(id, post);
            return post;
        }

        if (id != 0) {
            if (listPost.containsKey(id)) {
                listPost.replace(id, post);
                return post;
            }

            return new Post(0, "Пост не найден");
        }
        return post;
    }

    public void removeById(long id) {
        listPost.remove(id);
    }
}
