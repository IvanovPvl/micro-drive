package io.microdrive.auth.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import io.microdrive.auth.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
