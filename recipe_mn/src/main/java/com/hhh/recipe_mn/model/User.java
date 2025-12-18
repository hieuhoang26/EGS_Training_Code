package com.hhh.recipe_mn.model;


import com.hhh.recipe_mn.utlis.Gender;
import com.hhh.recipe_mn.utlis.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

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
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

