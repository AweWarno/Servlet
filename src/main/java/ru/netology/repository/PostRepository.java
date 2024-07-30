package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;

// Stub
public class PostRepository {

    private List<Post> listPost;
    private Integer idPost = 0;

    public List<Post> all() {
        return listPost;
    }

    public Optional<Post> getById(long id) {
        return Optional.empty();
    }

    public Post save(Post post) {
        long id = post.getId();
        String content = post.getContent();

        if (id == 0) {
            post.setId(id + 1);
            listPost.add(post);
            return post;
        }

        if (id != 0) {
            for (Post list : listPost) {
                if (list.getId() == id) {
                    list.setContent(content);
                    return list;
                }
            }

            return new Post(0, "Пост не найден");
        }
        return post;
    }

    public void removeById(long id) {
    }
}
