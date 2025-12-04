package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@NamedEntityGraph(
        name = "User.posts.tags",
        attributeNodes = {
                @NamedAttributeNode(value = "posts", subgraph = "posts-sub")
        }
        ,
        subgraphs = {
                @NamedSubgraph(
                        name = "posts-sub",
                        attributeNodes = {
                                @NamedAttributeNode("tags")
                        }
                )
        }
)
@BatchSize(size = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @BatchSize(size = 3)
    private List<Post> posts = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "author",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
//    private List<Comment> comments = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        System.out.println("User created: " + username);
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("User updated: " + username);
    }
}
