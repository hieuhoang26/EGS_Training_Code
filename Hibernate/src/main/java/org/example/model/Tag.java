package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", sequenceName = "tag_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    public void addPost(Post post) {
        posts.add(post);
        post.getTags().add(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.getTags().remove(this);
    }


}
