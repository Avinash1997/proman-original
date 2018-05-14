/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;

import javax.validation.constraints.NotNull;

import apps.proman.service.common.data.RequestContext;
import apps.proman.service.common.exception.EntityNotFoundException;
import apps.proman.service.user.entity.UserEntity;

/**
 * Interface for user related services.
 */
public interface UserService {

    UserEntity findActiveUser(@NotNull RequestContext requestContext, @NotNull String username) throws EntityNotFoundException;

}