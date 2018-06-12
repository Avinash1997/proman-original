/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthTokenVerifier.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.business;

import static apps.proman.service.common.data.DateTimeProvider.currentProgramTime;

import java.time.ZonedDateTime;

import apps.proman.service.common.data.DateTimeProvider;
import apps.proman.service.user.entity.UserAuthTokenEntity;
import apps.proman.service.user.model.UserAuthTokenStatus;

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
        final ZonedDateTime now = currentProgramTime();
        return userAuthToken.getExpiresAt().isBefore(now) || userAuthToken.getExpiresAt().isEqual(now);
    }

    private boolean isLoggedOut(final UserAuthTokenEntity userAuthToken) {
        return userAuthToken.getLogoutAt() != null;
    }

}