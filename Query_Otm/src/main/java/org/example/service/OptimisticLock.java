package org.example.service;

import org.example.config.DbConfig;
import org.example.model.Post;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OptimisticLock {

    public static void main(String[] args) {

        createPost();

        Session sessionA = DbConfig.getSessionFactory().openSession();
        Session sessionB = DbConfig.getSessionFactory().openSession();

        Post postA = sessionA.get(Post.class, 1L);  // version = 0
        Post postB = sessionB.get(Post.class, 1L);  // version = 0

        System.out.println("Initial version (A): " + postA.getVersion());
        System.out.println("Initial version (B): " + postB.getVersion());

        // A updates first
        Transaction txA = sessionA.beginTransaction();
        postA.setTitle("Updated by A");
        txA.commit(); // SUCCESS -- version = 1
        System.out.println("Version after A commit: " + postA.getVersion());

        //  B tries to update old version
        Transaction txB = sessionB.beginTransaction();
        postB.setTitle("Updated by B");

        try {
            txB.commit(); // FAIL
        } catch (Exception e) {
            System.out.println("\n>>> Optimistic lock detected!");
            e.printStackTrace();
            txB.rollback();
        }

        sessionA.close();
        sessionB.close();
    }

    private static void createPost() {
        Session session = DbConfig.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Post p = new Post();
        p.setTitle("Initial Title");

        session.save(p);
        tx.commit();
        session.close();
    }
}
