package io.microdrive.auth.service;

import io.microdrive.auth.domain.User;

import java.util.Optional;

public interface UserService {
    User create(User user);
}
