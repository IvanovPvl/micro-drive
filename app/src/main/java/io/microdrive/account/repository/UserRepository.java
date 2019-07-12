package io.microdrive.account.repository;

import io.microdrive.account.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
}
