/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import io.beingtechie.proman.api.user.service.entity.UserAuthTokenEntity;

/**
 * Implementation of {@link UserAuthDao}.
 */
@Repository
public class UserAuthDaoImpl extends UserBaseDaoImpl<UserAuthTokenEntity> implements UserAuthDao {

    @Override
    public UserAuthTokenEntity findToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery(UserAuthTokenEntity.TOKEN_BY_ACCESS_TOKEN, UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public UserAuthTokenEntity findToken(final Long userId, final String clientId, final String clientIpAddress) {
        final List<
                UserAuthTokenEntity> userAuthTokens = entityManager.createNamedQuery(UserAuthTokenEntity.TOKEN_BY_USER_AND_CLIENT, UserAuthTokenEntity.class) //
                        .setParameter("userId", userId) //
                        .setParameter("clientId", clientId) //
                        .setParameter("clientIpAddress", clientIpAddress) //
                        .getResultList();
        if (userAuthTokens.isEmpty()) {
            return null;
        }

        return userAuthTokens.get(0);
    }

}