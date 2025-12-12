package org.example.search_advance.repository;

import org.example.search_advance.model.Role;
import org.example.search_advance.model.UserHasRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHasRoleRepository extends JpaRepository<UserHasRole, Long> {
}
