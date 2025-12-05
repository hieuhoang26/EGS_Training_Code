package org.example.service;


import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.Tag;
import org.example.model.User;
import org.hibernate.Transaction;
import org.example.config.DbConfig;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class LifeCycleEx {
    /*
        Transient: Newly created object, not yet associated with a Session
        Persistent: Object is managed by the Session/EntityManager (saved/persisted or loaded from the DB)
        Detached: Object was once persistent, but its Session has been closed
        Removed: Object has been marked for deletion, will be deleted at flush/commit
     */
    /*
    In Spring Data JPA, have isNew() to determine if id exists
        - If id == null : INSERT
        - If id != null : UPDATE or SELECT then UPDATE
     */

    /*
    GET
        Method:
            - get(): Query DB now, return null if not found  ( hibernate)
            - load(): Load from DB, throw ObjectNotFoundException if not
            - find(): can return result or not (JPA)
     */
    public static void sampleGet() {
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            User user1 = session.load(User.class, 1L);
            System.out.println("user 1 " + user1.getUsername());

            System.out.println("---");
            User user2 = session.get(User.class, 1L); // Get Cache - no select
            System.out.println("user 2 " + user2.getUsername());

            System.out.println("u1 == u2 ? " + (user1 == user2));

            System.out.println("---");
            User user3 = session.find(User.class, 3L);
            System.out.println("user 3 " + user3.getUsername());

        }
    }

    /*
    INSERT: entity is TRANSIENT (no ID)
        Method:
            - save(): return ID. Write to persistence context immediately, INSERT will run on flush/commit (Hibernate).
            - persist(): Not return ID, chỉ INSERT khi flush/commit
     */
    public static void sampleInsert() {
        Transaction tx = null;
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();


            User user = new User();
            user.setUsername("Hibernate");

            List<Post> posts = new ArrayList<>();
            posts.add(new Post("Title", "HaiHai"));

            user.setPosts(posts);

            // --> TRANSIENT
            System.out.println("Before save | isNew = " + user.isNew());
            Long userId = (Long) session.save(user);

            System.out.println("Saved Post ID: " + userId);

            // --> DETACHED
//            session.evict(user);
//            Long userId2 = (Long) session.save(user); // Create new Persistent Instance --> duplicate
//            System.out.println("Saved Post ID: " + userId2);

            // FLUSH - changes to DB
            session.flush();
            System.out.println("After save | isNew = " + user.isNew());

            tx.commit();

            // after commit --> DETACHED
            System.out.println("Managed by session ? " + session.contains(user));

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /*
    UPDATE: entity is PERSISTENT and fields changed (dirty checking)
        Method:
            - update(): Re-attach detached entity, throw exception if existed same instance (= Id) in persistent context
            - merge(): Copy state from detached to persistent, không throw exception
            - saveOrUpdate(): ~ update()
                + exist in PC --> not do
                + exist same instance in session (= ID) --> exception
                + Not have ID/ not save in DB --> save()
                + update()

     */
    public static void sampeUpdate_merge() {
        Transaction tx = null;
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = new User();
            user.setUsername("H1");

            Long userId = (Long) session.save(user);

            System.out.println("Persistent: " + session.contains(user));

            session.evict(user);
            System.out.println("Detached: " + !session.contains(user));

            user.setUsername("H2");
            User user2 = session.merge(user);

            System.out.println("Merge in Persistent: " + session.contains(user2));
            System.out.println("Before == After: " + (user == user2));
            session.flush();

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public static void sampleUpdate() {
        Transaction tx = null;
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = session.get(User.class, 1L); // Load from DB → PERSISTENT
            System.out.println("Loaded username = " + user.getUsername());

            user.setUsername("updated_user"); // Mark entity as DIRTY (dirty checking)

            session.update(user);
            // Or
            // No explicit update() needed!
            // Hibernate will automatically UPDATE at flush.
            // session.flush(); // triggers UPDATE SQL

            tx.commit();
        }
    }

    public static void sampleUpdate_saveOrUpdate() {
        Transaction tx = null;
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = new User();
            user.setId(20L);
            user.setUsername("H1");
            ;
            session.saveOrUpdate(user);

            User user2 = new User();
            user2.setId(20L);
            user2.setUsername("H2");
            ;
            session.saveOrUpdate(user2);

            tx.commit();
        }
    }
    /*
    DELETE
        Method:
            - evict()
            - clear(): Detach all objects from the session --> DETACHED
            - delete() : Load instance --> delete
            - remove() : ~ delete (JPA)
     */
    public static void sampleDelete() {
        Transaction tx = null;
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = session.get(User.class, 15L); // PERSISTENT

            System.out.println("Loaded user to delete: " + user.getUsername());

            session.delete(user); // Mark  REMOVED
            System.out.println("Persistent: " + session.contains(user));
            session.flush(); // Executes DELETE SQL

            tx.commit();
        }
    }
    /*
    Other:
        - refresh(): refresh instance in PC
     */


    public static void main(String[] args) {
        sampleGet();
//        sampleInsert();
//        sampeUpdate_merge();
//        sampleUpdate_saveOrUpdate();
    }


}
