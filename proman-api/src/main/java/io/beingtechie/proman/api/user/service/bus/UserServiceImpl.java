/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.bus;

import javax.inject.Inject;

import io.beingtechie.proman.api.user.service.UserErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.beingtechie.proman.api.common.data.RequestContext;
import io.beingtechie.proman.api.common.exception.EntityNotFoundException;
import io.beingtechie.proman.api.user.service.dao.UserDao;
import io.beingtechie.proman.api.user.service.entity.UserEntity;

/**
 * Implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserEntity findByUsername(final RequestContext requestContext, final String username) throws EntityNotFoundException {
        final UserEntity userEntity = userDao.findByEmail(username);
        if (userEntity == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_002, username);
        }
        return userEntity;
    }

}