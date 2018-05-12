/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;

import javax.validation.constraints.NotNull;

import apps.proman.service.common.data.RequestContext;
import apps.proman.service.common.exception.AuthenticationFailedException;
import apps.proman.service.common.exception.AuthorizationFailedException;

/**
 * Interface for authentication related services.
 */
public interface AuthenticationService {

    String authenticate(@NotNull RequestContext requestContext, @NotNull String username, @NotNull String password) throws AuthenticationFailedException, AuthorizationFailedException;

    void logout(@NotNull RequestContext requestContext, @NotNull String accessToken) throws AuthorizationFailedException;

}