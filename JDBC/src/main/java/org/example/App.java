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

        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();

        System.out.println("Find by Id = " + userRepository.findById(1));
        System.out.println("Find all :" + userRepository.findAll());

        // Transaction
//        User u = new User("john", "john@gmail.com", "12345");
//        List<Post> newPosts = List.of(
//                new Post(0, "T1", "Content A"),
//                new Post(0, "T2", "Content B")
//        );
//        postRepository.createUserPosts(u, newPosts);

        // Create batch posts
//        List<Post> batch = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            batch.add(new Post(1, "Batch " + i, "Content " + i));
//        }
//        postRepository.saveBatch(batch);

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
