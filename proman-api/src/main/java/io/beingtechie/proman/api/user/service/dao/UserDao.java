/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.dao;

import javax.validation.constraints.NotNull;

import io.beingtechie.proman.api.common.dao.BaseDao;
import io.beingtechie.proman.api.user.service.entity.UserEntity;

/**
 * DAO abstraction for {@link UserEntity}.
 */
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity findByEmail(@NotNull String email);

}