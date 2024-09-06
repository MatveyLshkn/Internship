package lma.service;

import lma.entity.TokenPair;
import lma.entity.User;
import lma.exception.UserNotFoundException;
import lma.exception.UserWithEmailNotFoundException;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;

import static lma.constants.ExceptionConstants.USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE;
import static lma.constants.ExceptionConstants.USER_NOT_FOUND_BY_USERNAME_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();

        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new UserWithEmailNotFoundException(USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE.formatted(email));
        }

        return new DefaultOidcUser(user.getAuthorities(), oidcUser.getIdToken());
    }
}
