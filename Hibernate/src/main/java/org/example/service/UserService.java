package org.example.service;

import org.example.config.DbConfig;
import org.example.dao.PostDao;
import org.example.dao.UserDao;
import org.example.model.Post;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserService {
    private final UserDao userDao = new UserDao();
    private final PostDao postDao = new PostDao();

    public User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userDao.saveUser(user);
    }

    public Post createPost(Long userId, String title, String content) {
//        User user = userDao.findUser(userId); // end session
//        Post post = new Post();
//        post.setTitle(title);
//        post.setContent(content);
//        post.setUser(user);
//        user.getPosts().add(post); // LazyInitializationException
//        return postDao.savePost(post);

        Transaction tx = null;
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // User + posts proxy trong session
            User user = session.get(User.class, userId);

            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setUser(user);

            user.getPosts().add(post);

            // Persist post (user cũng được)
            session.persist(post);

            tx.commit();
            return post;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
