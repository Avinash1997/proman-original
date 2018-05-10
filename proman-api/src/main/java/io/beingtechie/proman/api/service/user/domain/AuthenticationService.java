/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user.domain;

import javax.validation.constraints.NotNull;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.common.exception.AuthenticationFailedException;
import io.beingtechie.proman.api.common.exception.AuthorizationFailedException;
import io.beingtechie.proman.api.service.user.model.AuthorizedUser;

/**
 * Interface for authentication related services.
 */
public interface AuthenticationService {

    String authenticate(@NotNull RequestContext requestContext, @NotNull String username, @NotNull String password) throws AuthenticationFailedException, AuthorizationFailedException;

    void logout(@NotNull RequestContext requestContext, @NotNull String accessToken) throws AuthorizationFailedException;

}