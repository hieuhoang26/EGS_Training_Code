package org.example.utils;

import org.example.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

public class Utils {

    public static void printStatistics(Session session) {
        Statistics stats = session.getSessionFactory().getStatistics();
        System.out.println("Query Count: " + stats.getQueryExecutionCount());
        System.out.println("Entity Load Count: " + stats.getEntityLoadCount());
        System.out.println("Collection Load Count: " + stats.getCollectionLoadCount());
        System.out.println("Prepare Statement Count: " + stats.getPrepareStatementCount());
        stats.clear();
    }
    public static void printLoadedUser(User u) {
        System.out.println("---- USER ----");
        System.out.println("User ID       : " + u.getId());
//        System.out.println("Username      : " + u.getUsername());

        System.out.println("\n---- POSTS ----");
        System.out.println("Posts loaded? : " + Hibernate.isInitialized(u.getPosts()));
        u.getPosts().forEach(p -> {
            System.out.println("  Post ID     : " + p.getId());
//            System.out.println("  Title       : " + p.getTitle());
            System.out.println("  Tags init?  : " + Hibernate.isInitialized(p.getTags()));
            System.out.println("  Comments init? : " + Hibernate.isInitialized(p.getComments()));
            System.out.println("\n----");
        });
    }
}
