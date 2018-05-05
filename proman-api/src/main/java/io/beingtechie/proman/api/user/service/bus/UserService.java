/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.bus;

import javax.validation.constraints.NotNull;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.common.exception.EntityNotFoundException;
import io.beingtechie.proman.api.user.service.entity.UserEntity;

/**
 * Interface for user related services.
 */
public interface UserService {

    UserEntity findByUsername(@NotNull RequestContext requestContext, @NotNull String username) throws EntityNotFoundException;

}