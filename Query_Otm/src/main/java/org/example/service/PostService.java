package org.example.service;

import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import java.util.List;

import static org.example.utils.Utils.printStatistics;

public class PostService {

    public void exNPlusOneProblem(Session session) {
        String sql = "FROM User ";
        List<User> users = session.createQuery(sql, User.class)
                .getResultList();

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

    /*
        JOIN FETCH
     */




}
