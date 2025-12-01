package org.example;

import org.example.config.DbConfig;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        DbConfig.getConnection();
        System.out.println("Connection Established successfully");

        User u = new User("nam", "nam@gmail.com", "12345");

        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();

        // CREATE
        userRepository.save(u);
        // GET user by Id
        System.out.println("Find by Id = " + userRepository.findById(1));
        // GET all
        System.out.println("Find all :" + userRepository.findAll());
        // Update
        User u2 = new User("nam", "nam@gmail.com", "hahaha");
        userRepository.update(u2);

        // Transaction
        List<Post> newPosts = List.of(
                new Post(0, "T1", "Content A"),
                new Post(0, "T2", "Content B")
        );
        postRepository.createUserPosts(u, newPosts);

        // Create batch posts
        List<Post> batch = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            batch.add(new Post(1, "Batch " + i, "Content " + i));
        }
        postRepository.saveBatch(batch);

        // GET post by UserId
        postRepository.findUserWithPostsJoin(1)
                .forEach(System.out::println);




        // Test pool
//        userRepository.findAll();
//        userRepository.findAll();
//        userRepository.findAll();

//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 5; i++) {
//            executor.submit(() -> userRepository.findAll());
//        }
//
//        executor.shutdown();


    }
}
