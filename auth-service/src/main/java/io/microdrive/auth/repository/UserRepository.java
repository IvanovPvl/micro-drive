package io.microdrive.auth.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import io.microdrive.auth.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {}
