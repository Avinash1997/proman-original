/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthorizationServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.bus;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.common.exception.AuthorizationFailedException;
import io.beingtechie.proman.api.user.service.dao.UserAuthDao;
import io.beingtechie.proman.api.user.service.dao.UserDao;
import io.beingtechie.proman.api.user.model.LogoutAction;
import io.beingtechie.proman.api.user.model.UserStatus;
import io.beingtechie.proman.api.user.service.entity.UserAuthTokenEntity;
import io.beingtechie.proman.api.user.service.entity.UserEntity;
import io.beingtechie.proman.api.user.service.manager.AuthorizationManager;
import io.beingtechie.proman.api.user.service.manager.UserAuthTokenVerifier;

/**
 * Implementation of {@link AuthorizationService}.
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    @Inject
    private UserDao userDao;

    @Inject
    private UserAuthDao userAuthDao;

    @Inject
    private AuthorizationManager authorizationManager;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void loginAttemptFailed(final RequestContext requestContext, final UserEntity userEntity) {
        int failedLoginCount = userEntity.getFailedLoginCount();
        if (failedLoginCount < 5) {
            failedLoginCount++;
            userEntity.setFailedLoginCount(failedLoginCount);
        } else {
            userEntity.setStatus(UserStatus.LOCKED.getCode());
        }

        userDao.update(userEntity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public UserAuthTokenEntity authorizeAccessToken(RequestContext requestContext, UserEntity userEntity) throws AuthorizationFailedException {

        final UserAuthTokenEntity userAuthToken = userAuthDao.findToken(userEntity.getId(), requestContext.getClientId(), requestContext.getClientIpAddress());
        final UserAuthTokenVerifier userAuthTokenVerifier = new UserAuthTokenVerifier(userAuthToken);

        switch (userAuthTokenVerifier.getStatus()) {
            case NOT_FOUND:
                return issueNewToken(requestContext, userEntity);
            case ACTIVE:
            case CONCURRENT_LOGIN:
                return userAuthToken;
            case LOGGED_OUT:
                return issueNewToken(requestContext, userEntity);
            case EXPIRED:
                performLogout(userAuthToken, LogoutAction.EXPIRED, requestContext.getRequestTime());
                return issueNewToken(requestContext, userEntity);
            default:
                throw new IllegalArgumentException("Invalid status");
        }

    }

    @Override
    public UserAuthTokenEntity findAccessToken(final RequestContext requestContext, final String accessToken) {
        return userAuthDao.findToken(accessToken);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void logout(final RequestContext requestContext, final UserAuthTokenEntity userAuthToken, final LogoutAction logoutAction) {
        performLogout(userAuthToken, logoutAction, requestContext.getRequestTime());
    }

    private void performLogout(final UserAuthTokenEntity userAuthToken, final LogoutAction logoutAction, final ZonedDateTime logoutTime) {
        userAuthToken.setLogoutAt(logoutTime);
        userAuthToken.setLogoutAction(logoutAction.getCode());
        userAuthDao.update(userAuthToken);
    }

    private UserAuthTokenEntity issueNewToken(final RequestContext requestContext, final UserEntity userEntity) {
        final String authToken = authorizationManager.generateToken(requestContext, userEntity);
        LOGGER.debug("Generated authToken: " + authToken);

        final UserAuthTokenEntity authTokenEntity = new UserAuthTokenEntity();
        authTokenEntity.setUser(userEntity);
        authTokenEntity.setAccessToken(authToken);
        authTokenEntity.setClientId(requestContext.getClientId());
        authTokenEntity.setClientIpAddress(requestContext.getClientIpAddress());
        authTokenEntity.setLoginAt(requestContext.getRequestTime());

        // FIXME - read this from configuration
        authTokenEntity.setExpiryAt(requestContext.getRequestTime().plusHours(8));

        userAuthDao.create(authTokenEntity);

        userEntity.setLastLoginAt(requestContext.getRequestTime());
        userDao.update(userEntity);

        return authTokenEntity;
    }

}