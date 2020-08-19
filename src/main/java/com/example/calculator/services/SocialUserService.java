package com.example.calculator.services;


import com.example.calculator.exception.UserNotFoundException;
import com.example.calculator.model.Operation;
import com.example.calculator.model.SocialUser;
import com.example.calculator.repository.SocialUserRepository;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Value
@Service
@Log4j2
public class SocialUserService {

    SocialUserRepository repo;

    public void addUserSocial(OAuth2AuthenticationToken socialUser) {

        Map<String, Object> attributes = socialUser.getPrincipal().getAttributes();

        if (!repo.findByEmailAndRegId((String) attributes.get("email"), socialUser.getAuthorizedClientRegistrationId()).isPresent()) {
            log.info("ENTERED TO SAVE");
            SocialUser user = new SocialUser((String) attributes.get("name"), (String) attributes.get("email"), socialUser.getAuthorizedClientRegistrationId());
            repo.save(user);
        }

    }

    public SocialUser findByEmailAndRegID(String email, String regId) {
        Optional<SocialUser> user = repo.findByEmailAndRegId(email, regId);
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void addOperation(String email,String regId, Operation operation) {
        SocialUser user = findByEmailAndRegID(email, regId);
        user.getOperations().add(operation);
        repo.save(user);
    }

}
