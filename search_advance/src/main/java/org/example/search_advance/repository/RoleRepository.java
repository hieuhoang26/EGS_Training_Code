package org.example.search_advance.repository;

import org.example.search_advance.model.Role;
import org.example.search_advance.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @EntityGraph(value = "Role.withPermissions", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Role> findWithPermissionsByName(String name);
}
