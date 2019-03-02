package io.microdrive.auth.service;

import io.microdrive.auth.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> create(User user);
}
