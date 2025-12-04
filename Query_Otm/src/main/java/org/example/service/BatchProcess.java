package org.example.service;

import org.example.model.Post;
import org.example.model.User;
import org.hibernate.Session;

import java.util.List;

import static org.example.utils.Utils.printStatistics;

public class BatchProcess {
    public  void demonstrateBatchFetching(Session em) {
        System.out.println("=== Batch Fetching Demo ===");

        List<User> users = em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
        for (User user : users) {
            System.out.println("User: " + user.getUsername());
            for (Post post : user.getPosts()) { // Posts loaded in batches
                System.out.println("  Post: " + post.getTitle());
            }
        }

        printStatistics(em);
    }
}
