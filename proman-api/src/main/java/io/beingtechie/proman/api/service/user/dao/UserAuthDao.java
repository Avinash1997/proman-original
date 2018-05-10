/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user.dao;

import javax.validation.constraints.NotNull;

import io.beingtechie.proman.api.common.dao.BaseDao;
import io.beingtechie.proman.api.service.user.entity.UserAuthTokenEntity;

/**
 * DAO interface for {@link UserAuthTokenEntity}.
 */
public interface UserAuthDao extends BaseDao<UserAuthTokenEntity> {

    UserAuthTokenEntity findToken(@NotNull String accessToken);

    /**
     * @param userId
     * @param clientId
     * @param clientIpAddress
     * @return
     */
    UserAuthTokenEntity findToken(@NotNull Long userId, @NotNull String clientId, @NotNull String clientIpAddress);

}