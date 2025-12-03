package org.example.config;

import lombok.Getter;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.Tag;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class DbConfig {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Post.class);
            configuration.addAnnotatedClass(Comment.class);
            configuration.addAnnotatedClass(Tag.class);

            return configuration.buildSessionFactory();
        }
        catch (Exception e) {
            throw new RuntimeException("Error building SessionFactory", e);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public  static void shutDown(){
        sessionFactory.close();
    }
}

