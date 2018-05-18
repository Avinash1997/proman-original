/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;

import javax.validation.constraints.NotNull;

import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.common.exception.AuthorizationFailedException;
import apps.proman.service.user.model.AuthorizedUser;

/**
 * Interface for authentication related services.
 */
public interface AuthenticationService {

    AuthorizedUser authenticate(@NotNull String username, @NotNull String password) throws ApplicationException;

}