/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthTokenVerifier.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.manager;

import java.time.ZonedDateTime;

import io.beingtechie.proman.api.common.data.DateTimeProvider;
import io.beingtechie.proman.api.user.service.entity.UserAuthTokenEntity;

/**
 * Verifies the authentication token and capture the status.
 */
public final class UserAuthTokenVerifier {

    private final UserAuthTokenStatus status;

    public UserAuthTokenVerifier(final UserAuthTokenEntity userAuthToken) {

        if (userAuthToken == null) {
            status = UserAuthTokenStatus.NOT_FOUND;
        } else if (isLoggedOut(userAuthToken)) {
            status = UserAuthTokenStatus.LOGGED_OUT;
        } else if (isExpired(userAuthToken)) {
            status = UserAuthTokenStatus.EXPIRED;
        } else {
            status = UserAuthTokenStatus.ACTIVE;
        }
    }

    public UserAuthTokenStatus getStatus() {
        return status;
    }

    private boolean isExpired(final UserAuthTokenEntity userAuthToken) {
        final ZonedDateTime now = DateTimeProvider.getInstance().currentProgramTime();
        return userAuthToken.getExpiryAt().isBefore(now) || userAuthToken.getExpiryAt().isEqual(now);
    }

    private boolean isLoggedOut(final UserAuthTokenEntity userAuthToken) {
        return userAuthToken.getLogoutAt() != null;
    }

}