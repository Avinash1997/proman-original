/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthorizationService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;

import javax.validation.constraints.NotNull;

import apps.proman.service.common.data.RequestContext;
import apps.proman.service.common.exception.AuthorizationFailedException;
import apps.proman.service.user.entity.UserAuthTokenEntity;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.LogoutAction;

/**
 * Interface for user authentication related services.
 */
public interface AuthorizationService {

    void loginAttemptFailed(@NotNull RequestContext requestContext, @NotNull UserEntity userEntity);

    String authorizeAccessToken(@NotNull RequestContext requestContext, @NotNull UserEntity userEntity) throws AuthorizationFailedException;

    UserAuthTokenEntity findAccessToken(@NotNull RequestContext requestContext, @NotNull String accessToken) throws AuthorizationFailedException;

    void logout(@NotNull RequestContext requestContext, @NotNull UserAuthTokenEntity userAuthToken, @NotNull LogoutAction logoutAction);

}