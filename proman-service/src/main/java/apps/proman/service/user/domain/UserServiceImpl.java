/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import apps.proman.service.common.data.RequestContext;
import apps.proman.service.common.exception.EntityNotFoundException;
import apps.proman.service.user.UserErrorCode;
import apps.proman.service.user.dao.UserDao;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.UserStatus;

/**
 * Implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserEntity findActiveUser(final RequestContext requestContext, final String username) throws EntityNotFoundException {
        final UserEntity userEntity = userDao.findByEmail(username);
        if (userEntity == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_002, username);
        }
        return userEntity;
    }

}