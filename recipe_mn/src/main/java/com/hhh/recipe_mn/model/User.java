package com.hhh.recipe_mn.model;


import com.hhh.recipe_mn.utlis.Gender;
import com.hhh.recipe_mn.utlis.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "User.roles.permissions",
        attributeNodes = {
                @NamedAttributeNode(
                        value = "roles",
                        subgraph = "rolesSubgraph"
                )
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "rolesSubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("permissions")
                        }
                )
        }
)
public class User extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email" , unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private UserStatus status;

    // users_roles
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // recipes
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ShoppingList> shoppingLists = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<String> roleList = roles.stream().map(Role::getName).toList();
//        return roleList.stream().map(s -> new SimpleGrantedAuthority(s.toUpperCase())).toList();
        Set<GrantedAuthority> authorities = new HashSet<>();

        // ROLE_
        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority( role.getName()))
        );

        // PERMISSION
        roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .forEach(p ->
                        authorities.add(new SimpleGrantedAuthority(p))
                );

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(status);
    }
}

