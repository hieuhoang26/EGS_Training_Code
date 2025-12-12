package org.example.search_advance.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role")

@NamedEntityGraph(
        name = "Role.withPermissions",
        attributeNodes = @NamedAttributeNode(
                value = "roles",
                subgraph = "permissionDetails"
        ),
        subgraphs = @NamedSubgraph(
                name = "permissionDetails",
                attributeNodes = @NamedAttributeNode("permission")
        )
)
public class Role extends AbstractEntity<Integer> {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

//    @OneToMany(mappedBy = "user")
//    private Set<User> users;

    @OneToMany(mappedBy = "role")
    private Set<RoleHasPermission> roles = new HashSet<>();
}