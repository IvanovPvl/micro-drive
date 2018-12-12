package io.microdrive.auth.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import io.microdrive.common.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}
