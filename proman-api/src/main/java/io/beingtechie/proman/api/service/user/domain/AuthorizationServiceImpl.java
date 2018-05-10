/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthorizationServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user.domain;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.common.exception.AuthorizationFailedException;
import io.beingtechie.proman.api.service.user.dao.UserAuthDao;
import io.beingtechie.proman.api.service.user.dao.UserDao;
import io.beingtechie.proman.api.service.user.entity.UserAuthTokenEntity;
import io.beingtechie.proman.api.service.user.entity.UserEntity;
import io.beingtechie.proman.api.service.user.manager.UserAuthTokenVerifier;
import io.beingtechie.proman.api.service.user.model.LogoutAction;
import io.beingtechie.proman.api.service.user.model.UserStatus;
import io.beingtechie.proman.api.service.user.provider.token.JwtTokenProvider;
import io.beingtechie.proman.api.service.user.provider.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

/**
 * Implementation of {@link AuthorizationService}.
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthDao userAuthDao;


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
    public String authorizeAccessToken(RequestContext requestContext, UserEntity userEntity) throws AuthorizationFailedException {

        final UserAuthTokenEntity userAuthToken = userAuthDao.findToken(userEntity.getId(), requestContext.getClientId(), requestContext.getClientIpAddress());
        final UserAuthTokenVerifier userAuthTokenVerifier = new UserAuthTokenVerifier(userAuthToken);

        switch (userAuthTokenVerifier.getStatus()) {
            case NOT_FOUND:
                return issueNewToken(requestContext, userEntity);
            case ACTIVE:
            case CONCURRENT_LOGIN:
                return userAuthToken.getAccessToken();
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

    private String issueNewToken(final RequestContext requestContext, final UserEntity userEntity) {

        final ZonedDateTime requestTime = requestContext.getRequestTime();

        // FIXME - Read the expiration time from configuration
        final ZonedDateTime expirationTime = requestTime.plusHours(8);

        final JwtTokenProvider tokenProvider = new JwtTokenProvider(userEntity.getPassword());


        final Token tokenSpec = new Token.Builder().clientId(requestContext.getClientId()).clientIpAddress(requestContext.getClientIpAddress()) //
                .issuedTime(requestTime).expirationTime(expirationTime) //
                .userId(userEntity.getUuid()).userFirstname(userEntity.getFirstName()).userLastname(userEntity.getLastName()) //
                .userEmailAddress(userEntity.getEmail()).userMobilePhone(userEntity.getMobilePhone()).lastLoginAt(userEntity.getLastLoginAt()) //
                .roleId(userEntity.getRoleId()).roleName("Administrator").build();

        final String authToken = tokenProvider.serialize(tokenSpec);

        LOGGER.debug("Generated authToken: " + authToken);

        final UserAuthTokenEntity authTokenEntity = new UserAuthTokenEntity();
        authTokenEntity.setUser(userEntity);
        authTokenEntity.setAccessToken(authToken);
        authTokenEntity.setClientId(requestContext.getClientId());
        authTokenEntity.setClientIpAddress(requestContext.getClientIpAddress());
        authTokenEntity.setLoginAt(requestTime);
        authTokenEntity.setExpiryAt(expirationTime);

        userAuthDao.create(authTokenEntity);

        userEntity.setLastLoginAt(requestTime);
        userDao.update(userEntity);

        return authToken;
    }

}