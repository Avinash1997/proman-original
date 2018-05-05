/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthorizationManagerImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.manager;

import java.time.temporal.ChronoField;

import org.springframework.stereotype.Component;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.user.service.entity.UserEntity;
import io.beingtechie.proman.api.user.service.provider.token.JwtTokenProvider;
import io.beingtechie.proman.api.user.service.provider.token.Token;

/**
 * Implementation of {@link AuthorizationManager}.
 */
@Component
public class AuthorizationManagerImpl implements AuthorizationManager {

    @Override
    public String generateToken(final RequestContext requestContext, final UserEntity user) {

        final JwtTokenProvider tokenProvider = new JwtTokenProvider(user.getPassword());

        // FIXME - Read the expire time from configuration
        final Token tokenSpec = new Token(requestContext.getRequestId(), requestContext.getClientId(), user.getUuid(), requestContext.getClientIpAddress(), //
                requestContext.getRequestTime().getLong(ChronoField.INSTANT_SECONDS), requestContext.getRequestTime().plusHours(8).getLong(ChronoField.INSTANT_SECONDS));
        return tokenProvider.serialize(tokenSpec);
    }

}