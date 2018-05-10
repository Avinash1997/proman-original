/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthorizationService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user.domain;

import javax.validation.constraints.NotNull;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.common.exception.AuthorizationFailedException;
import io.beingtechie.proman.api.service.user.model.LogoutAction;
import io.beingtechie.proman.api.service.user.entity.UserAuthTokenEntity;
import io.beingtechie.proman.api.service.user.entity.UserEntity;

/**
 * Interface for user authentication related services.
 */
public interface AuthorizationService {

    void loginAttemptFailed(@NotNull RequestContext requestContext, @NotNull UserEntity userEntity);

    String authorizeAccessToken(@NotNull RequestContext requestContext, @NotNull UserEntity userEntity) throws AuthorizationFailedException;

    UserAuthTokenEntity findAccessToken(@NotNull RequestContext requestContext, @NotNull String accessToken) throws AuthorizationFailedException;

    void logout(@NotNull RequestContext requestContext, @NotNull UserAuthTokenEntity userAuthToken, @NotNull LogoutAction logoutAction);

}