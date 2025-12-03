package org.example;

import org.example.config.DbConfig;
import org.example.model.Post;
import org.example.model.User;
import org.example.service.ServiceEx;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ServiceEx service = new ServiceEx();

        User u = service.createUser("john123");

        Post post = service.createPost(u.getId(), "My Post", "Hello Hibernate!");

        System.out.println("Created Post: " + post.getTitle());

        try (Session session = DbConfig.getSessionFactory().openSession()){

            System.out.println("\n=== HQL: POSTS BY USER ID ===");
            List<Post> posts = service.getPostByUserId(session,3L);
            posts.forEach(p -> System.out.println(p.getId() + " - " + p.getTitle()));

            System.out.println("\n=== HQL: USER WITH POSTS ===");
            List<Object[]> result = service.getUserWithPosts(session);
            for (Object[] row : result) {
                System.out.println("User: " + row[0] + " - Post: " + row[1]);
            }

            System.out.println("\n=== Native SQL: ALL USERS ===");
            List<User> users = service.getAllUsers(session);
            users.forEach(System.out::println);


            System.out.println("\n=== Criteria: SEARCH POSTS  ===");
            List<Post> filtered = service.searchPosts(session,"My");
            filtered.forEach(p -> System.out.println(p.getId()));

            System.out.println("\n=== Criteria: GET POSTS BY USERNAME  ===");
            List<Post> filtered1 = service.findPostByUserName(session,"john123");
            filtered1.forEach(p -> System.out.println(p.getId()));

            System.out.println("\n=== Criteria: COUNT POSTS BY USERNAME  ===");
            List<Object[]> countList = service.countPostByUser(session);
            for (Object[] row : countList) {
                for (Object cell : row) {
                    System.out.println("  Value: " + cell);
                }
            }
            System.out.println("\n=== Criteria: PAGING POSTS ===");
            List<Post> paging = service.getPostsPage(session,0,3);
            paging.forEach(p -> System.out.println(p.getId()));

        }
        
        DbConfig.shutDown();

    }
}
