/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import apps.proman.service.common.data.RequestContext;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.common.exception.AuthenticationFailedException;
import apps.proman.service.common.exception.AuthorizationFailedException;
import apps.proman.service.common.exception.EntityNotFoundException;
import apps.proman.service.user.UserErrorCode;
import apps.proman.service.user.entity.UserAuthTokenEntity;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.AuthorizedUser;
import apps.proman.service.user.model.LogoutAction;
import apps.proman.service.user.model.UserRole;
import apps.proman.service.user.model.UserStatus;
import apps.proman.service.user.provider.PasswordCryptographyProvider;

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
    public AuthorizedUser authenticate(final RequestContext requestContext, final String username, final String password) throws ApplicationException {

        UserEntity userEntity;
        try {
            userEntity = userService.findUserByEmail(requestContext, username);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("User with username [{}] does not exist", username);
            throw new AuthenticationFailedException(UserErrorCode.USR_002);
        }

        if(UserStatus.LOCKED == UserStatus.get(userEntity.getStatus())) {
            LOGGER.warn("Status of the username [{}] is LOCKED", username);
            throw new AuthenticationFailedException(UserErrorCode.USR_006);
        }
        else if(UserStatus.INACTIVE == UserStatus.get(userEntity.getStatus())) {
            LOGGER.warn("Status of the username [{}] is LOCKED", username);
            throw new AuthenticationFailedException(UserErrorCode.USR_007);
        }
        else {
            final String encryptedPassword = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());
            if (!userEntity.getPassword().equals(encryptedPassword)) {
                LOGGER.warn("Password match failed for user [{}]", username);
                authorizationService.loginAttemptFailed(requestContext, userEntity);
                throw new AuthenticationFailedException(UserErrorCode.USR_003);
            }

            if(userEntity.getFailedLoginCount() > 0) {
                authorizationService.resetFailedLoginAttempt(requestContext, userEntity);
            }

            UserAuthTokenEntity userAuthToken = authorizationService.authorizeAccessToken(requestContext, userEntity);
            return authorizedUser(userEntity, userAuthToken);
        }

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

    private AuthorizedUser authorizedUser(final UserEntity userEntity, final UserAuthTokenEntity userAuthToken) {
        final AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setId(userEntity.getUuid());
        authorizedUser.setFirstName(userEntity.getFirstName());
        authorizedUser.setLastName(userEntity.getLastName());
        authorizedUser.setEmailAddress(userEntity.getEmail());
        authorizedUser.setMobilePhoneNumber(userEntity.getMobilePhone());
        authorizedUser.setLastLoginTime(userEntity.getLastLoginAt());
        authorizedUser.setStatus(UserStatus.get(userEntity.getStatus()));
        authorizedUser.setAccessToken(userAuthToken.getAccessToken());
        authorizedUser.setRole(new UserRole(userEntity.getRole().getUuid(), userEntity.getRole().getName()));
        return authorizedUser;
    }

}
