package org.example.dao;


import org.example.model.Post;

public class PostDao extends BaseDao {

    public Post savePost(Post post) {
        return save(post);
    }

    public Post findPost(Long id) {
        return find(Post.class, id);
    }
}
