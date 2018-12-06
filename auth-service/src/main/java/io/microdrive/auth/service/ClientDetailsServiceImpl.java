package io.microdrive.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import io.microdrive.auth.repository.OauthClientDetailsRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

@Primary
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OauthClientDetailsRepository clientDetailsRepository;

    public ClientDetailsServiceImpl(OauthClientDetailsRepository clientDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientDetailsRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientRegistrationException("Client Id not found"));
    }
}
