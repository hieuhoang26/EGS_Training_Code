package org.example.service;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.Tag;
import org.example.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Map;

import static org.example.utils.Utils.printLoadedUser;
import static org.example.utils.Utils.printStatistics;

public class EntityGraphEx {
    /*
    NamedEntityGraph
     */
    // Entity: User
    // Load User - fetch Post
    // each Post - fetch Comment, Tag

    //    @NamedEntityGraph(
//            name = "User.posts.comments",
//            attributeNodes = {
//                    @NamedAttributeNode(value = "posts", subgraph = "posts-sub")
//            },
//            subgraphs = {
//                    @NamedSubgraph(
//                            name = "posts-sub",
//                            attributeNodes = {
//                                    @NamedAttributeNode("comments"),
//                                    @NamedAttributeNode("tags")
//                            }
//                    )
//            }
//    )
    /**
     *  Using Named Entity Graph defined in @Entity
     */
    public void demonstrateNamedEntityGraph(Session session) {
        System.out.println("\n===Using Named Entity Graph ===");


        TypedQuery<User> query = session.createQuery(
                "SELECT u FROM User u WHERE u.id = :userId", User.class);

        query.setHint("javax.persistence.fetchgraph",
                session.getEntityGraph("User.posts.tags"));
        query.setParameter("userId", 1L);

        User user = query.getSingleResult();
        printLoadedUser(user);
        printStatistics(session);
    }
    /**
     * Different ways to use EntityGraph hints
     */
    public void demonstrateEntityGraphHints(EntityManager em) {
        Long userId = 1L;

        /*
            1. FETCHGRAPH
            - Only loads: posts + posts.tags
            - Attributes not in the graph => always LAZY
         */
        TypedQuery<User> fetchGraphQuery = em.createQuery(
                "SELECT u FROM User u WHERE u.id = :id", User.class);
        fetchGraphQuery.setParameter("id", userId);
        fetchGraphQuery.setHint(
                "javax.persistence.fetchgraph",
                em.getEntityGraph("User.posts.tags")
        );
        User user = fetchGraphQuery.getSingleResult();
        printLoadedUser(user);


        /*
            2. LOADGRAPH
            - Loads: posts + posts.tags (EAGER)
            - Other attributes follow their own fetch type:
                + EAGER stays EAGER
                + LAZY stays LAZY
         */
        TypedQuery<User> loadGraphQuery = em.createQuery(
                "SELECT u FROM User u WHERE u.id = :id", User.class);
        loadGraphQuery.setParameter("id", userId);
        loadGraphQuery.setHint(
                "javax.persistence.loadgraph",
                em.getEntityGraph("User.posts.tags")
        );
        User user1 = loadGraphQuery.getSingleResult();
        printLoadedUser(user1);

    }

    /**
     * Example 2: Creating Dynamic Entity Graph at runtime
     */
    public void demonstrateDynamicEntityGraph(Session session) {
        System.out.println("\n=== Dynamic Entity Graph ===");

        // Create entity graph dynamically
        EntityGraph<User> dynamicGraph = session.createEntityGraph(User.class);
        dynamicGraph.addAttributeNodes("posts");

        // Add subgraph for posts
        Subgraph<Post> postsGraph = dynamicGraph.addSubgraph("posts", Post.class);
        postsGraph.addAttributeNodes("tags");

        // Add subgraph for comments (nested)
//        Subgraph<Comment> commentsGraph = postsGraph.addSubgraph("comments", Comment.class);
//        commentsGraph.addAttributeNodes("author");

        // Use dynamic entity graph
        TypedQuery<User> query = session.createQuery(
                "SELECT u FROM User u WHERE u.id = :userId", User.class);

        query.setHint("javax.persistence.fetchgraph", dynamicGraph);
        query.setParameter("userId", 1L);

        User user = query.getSingleResult();

        System.out.println("User: " + user.getUsername());
        for (Post post : user.getPosts()) {
            System.out.println("  Post: " + post.getTitle());
            for (Tag tag : post.getTags()) {
                System.out.println("    Comment by: " + tag.getName());
            }
        }

        printStatistics(session);
    }





}
