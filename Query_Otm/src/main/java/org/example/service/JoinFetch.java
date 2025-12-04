package org.example.service;

import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.Tag;
import org.example.model.User;
import org.hibernate.Session;

import java.util.List;

import static org.example.utils.Utils.printStatistics;

public class JoinFetch {
    public void exJoin(Session session) {
        String sql = "SELECT u FROM User u JOIN  u.posts";
        List<User> users = session.createQuery(sql, User.class).getResultList();

        for (User u : users) {
            System.out.println("===" +
                    "User: " + u.getUsername() +
                    ", Posts: " +
                    u.getPosts().stream()
                            .map(Post::getTitle)
                            .toList()
            );

        }
        printStatistics(session);
    }
    public void exJoinFetch(Session session) {
        String sql = "SELECT u FROM User u JOIN FETCH  u.posts";
        List<User> users = session.createQuery(sql, User.class).getResultList();

        for (User u : users) {
            System.out.println("===" +
                    "User: " + u.getUsername() +
                    ", Posts: " +
                    u.getPosts().stream()
                            .map(Post::getTitle)
                            .toList()
            );

        }
        printStatistics(session);
    }
    public void fetchPostWithTags(Session session) {
        String hql = """
        SELECT DISTINCT p 
        FROM Post p 
        JOIN FETCH p.tags
        """;

        List<Post> posts = session.createQuery(hql, Post.class).getResultList();

        posts.forEach(p -> System.out.println(
                p.getTitle() + " -> Tags: " +
                        p.getTags().stream().map(Tag::getName).toList()
        ));
        printStatistics(session);
    }

    // throw: Error MultipleBagFetchException (List_List)
    public void fetchUserPostsComments(Session session) {
        String hql = """
        SELECT DISTINCT u 
        FROM User u
        JOIN FETCH u.posts p
        JOIN FETCH p.comments c
        """;

        List<User> users = session.createQuery(hql, User.class).getResultList();

        users.forEach(u -> {
            System.out.println("User: " + u.getUsername());
            u.getPosts().forEach(p -> {
                System.out.println("  Post: " + p.getTitle());
                System.out.println("    Comments: " +
                        p.getComments().stream().map(Comment::getText).toList()
                );
            });
        });
    }


}
