package io.microdrive.auth.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import io.microdrive.auth.domain.OauthClientDetails;

@Repository
public interface OauthClientDetailsRepository extends CrudRepository<OauthClientDetails, String> {
    Optional<OauthClientDetails> findByClientId(String clientId);
}
