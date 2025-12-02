package org.example;

import org.example.model.Post;
import org.example.model.User;
import org.example.service.UserService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UserService userService = new UserService();

        User u = userService.createUser("john123");

        Post p = userService.createPost(u.getId(), "My Post", "Hello Hibernate!");

        System.out.println("Created Post: " + p.getTitle());
    }
}
