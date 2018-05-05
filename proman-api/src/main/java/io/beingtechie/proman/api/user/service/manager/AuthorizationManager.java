/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthorizationManager.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.manager;

import javax.validation.constraints.NotNull;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.user.service.entity.UserEntity;

/**
 * Manager for authentication token serialization and deserialization.
 */
public interface AuthorizationManager {

    /**
     * This method generates authentication token specification based on the provided {@link RequestContext}.
     * 
     * @param requestContext containing request meta information.
     * @param user user whose authorized access has to be granted.
     * @return generated token in raw format.
     */
    String generateToken(@NotNull RequestContext requestContext, @NotNull UserEntity user);

}