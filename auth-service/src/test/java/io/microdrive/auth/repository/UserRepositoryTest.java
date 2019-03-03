package io.microdrive.auth.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Collections;
import static org.junit.Assert.*;

import io.microdrive.auth.domain.User;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        User user = User.builder()
                .username("pavel")
                .password("123")
                .roles(Collections.singletonList("user"))
                .build();

        User savedUser = this.userRepository.save(user);
        assertNotNull(savedUser.getId());
    }
}
