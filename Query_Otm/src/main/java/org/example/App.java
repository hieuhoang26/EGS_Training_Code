package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.config.DbConfig;
import org.example.service.BatchProcess;
import org.example.service.EntityGraphEx;
import org.example.service.JoinFetch;
import org.example.service.PostService;
import org.hibernate.Session;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        PostService postService = new PostService();

        try (Session session = DbConfig.getSessionFactory().openSession()) {
            System.out.println("\n----N-1 Problem POSTS ----");
            postService.exNPlusOneProblem(session);
        }
        /*
        JOIN FETCH
         */
        JoinFetch joinFetch = new JoinFetch();
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            System.out.println("\n----Join----");
            joinFetch.exJoin(session);
        }
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            System.out.println("\n----Join Fetch----");
            joinFetch.exJoinFetch(session);
        }
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            System.out.println("\n----Join Fetch Many_Many----");
            joinFetch.fetchPostWithTags(session);
        }

        /*
        EntityGraph
         */
        EntityGraphEx entityGraphEx = new EntityGraphEx();
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            System.out.println("\n----Join Fetch Many_Many----");
            entityGraphEx.demonstrateNamedEntityGraph(session);
        }

        try (Session session = DbConfig.getSessionFactory().openSession()) {
            System.out.println("\n---- FETCH vs LOAD ----");
            entityGraphEx.demonstrateEntityGraphHints(session);
        }
        /*
        Batch
         */
        BatchProcess batchProcess = new BatchProcess();
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            System.out.println("\n----Batch----");
            batchProcess.demonstrateBatchFetching(session);
        }

    }


}
