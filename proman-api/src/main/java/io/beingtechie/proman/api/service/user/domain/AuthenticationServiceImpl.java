/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user.domain;


import io.beingtechie.proman.api.service.user.UserErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.common.exception.AuthenticationFailedException;
import io.beingtechie.proman.api.common.exception.AuthorizationFailedException;
import io.beingtechie.proman.api.common.exception.EntityNotFoundException;

import io.beingtechie.proman.api.service.user.model.AuthorizedUser;
import io.beingtechie.proman.api.service.user.model.LogoutAction;
import io.beingtechie.proman.api.service.user.model.UserStatus;
import io.beingtechie.proman.api.service.user.entity.UserAuthTokenEntity;
import io.beingtechie.proman.api.service.user.entity.UserEntity;
import io.beingtechie.proman.api.service.user.manager.UserAuthTokenVerifier;
import io.beingtechie.proman.api.service.user.provider.PasswordCryptographyProvider;

/**
 * Implementation of {@link AuthenticationService}.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String authenticate(final RequestContext requestContext, final String username, final String password) throws AuthenticationFailedException, AuthorizationFailedException {

        UserEntity userEntity;
        try {
            userEntity = userService.findByUsername(requestContext, username);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("User with username [{}] does not exist", username);
            throw new AuthenticationFailedException(UserErrorCode.USR_002);
        }

        final String encryptedPassword = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());
        if (!userEntity.getPassword().equals(encryptedPassword)) {
            LOGGER.warn("Password match failed for user [{}]", username);
            authorizationService.loginAttemptFailed(requestContext, userEntity);
            throw new AuthenticationFailedException(UserErrorCode.USR_003);
        }

        return authorizationService.authorizeAccessToken(requestContext, userEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void logout(final RequestContext requestContext, final String accessToken) throws AuthorizationFailedException {

        final UserAuthTokenEntity userAuthToken = authorizationService.findAccessToken(requestContext, accessToken);
        final UserAuthTokenVerifier userAuthTokenVerifier = new UserAuthTokenVerifier(userAuthToken);

        switch (userAuthTokenVerifier.getStatus()) {
            case ACTIVE:
                authorizationService.logout(requestContext, userAuthToken, LogoutAction.USER);
                break;
            case EXPIRED:
                authorizationService.logout(requestContext, userAuthToken, LogoutAction.EXPIRED);
                break;
            case NOT_FOUND:
            case LOGGED_OUT:
            case CONCURRENT_LOGIN:
                throw new AuthorizationFailedException(UserErrorCode.USR_005);
        }
    }

}
