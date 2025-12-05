package org.example.service;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.example.config.DbConfig;
import org.example.dao.PostDao;
import org.example.dao.UserDao;
import org.example.model.Post;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ServiceEx {
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

            // User + posts proxy in session
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

    // HQL
    // Get post by user id
    public List<Post> getPostByUserId(Session session,Long userId) {
        String hql = "FROM Post p WHERE p.user.id = :uid ORDER BY p.id DESC";
        return session.createQuery(hql, Post.class)
                .setParameter("uid", userId)
                .list();
    }
    // Get List User - Post
    public List<Object[]> getUserWithPosts(Session session) {
        String hql = """
                    SELECT u.username, p.title
                    FROM User u
                    JOIN u.posts p
                """;
        return session.createQuery(hql, Object[].class).getResultList();
    }
    // Native
    // Get
    public List<User> getAllUsers(Session session) {
        String sql = "SELECT * FROM users";
        return session.createNativeQuery(sql, User.class).getResultList();
    }
    // Criteria
    public List<Post> searchPosts(Session session,String keyword) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        Root<Post> root = cq.from(Post.class);
        Predicate predicate = cb.like(root.get("title"), "%" + keyword + "%");
        cq.select(root).where(predicate);
        return session.createQuery(cq).getResultList();
    }
    // Find post by username
    public List<Post> findPostByUserName(Session session, String name){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        Root<Post> root = cq.from(Post.class);
        Join<Post,User> userJoin = root.join("user");
        Predicate predicate = cb.equal(userJoin.get("username"),name);
        cq.select(root).where(predicate);
        return session.createQuery(cq).getResultList();
    }
    // Count Post By User
    public List<Object[]> countPostByUser(Session session){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Post> root = cq.from(Post.class);
        Join<Post,User> userJoin = root.join("user");

        cq.multiselect(
                userJoin.get("username"),
                cb.count(root.get("id"))
        ).groupBy(userJoin.get("username"));
        return session.createQuery(cq).getResultList();
    }
    // Paging Post
    public List<Post> getPostsPage(Session session, int page, int size){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        Root<Post> root = cq.from(Post.class);
        cq.select(root);

        return session.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }



}
