/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import apps.proman.service.user.entity.UserEntity;

/**
 * Implementation of {@link UserDao}.
 */
@Repository
public class UserDaoImpl extends UserBaseDaoImpl<UserEntity> implements UserDao {

    @Override
    public UserEntity findByEmail(final String email) {
        try {
            return entityManager.createNamedQuery(UserEntity.BY_EMAIL, UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException noResultExc) {
            return null;
        }
    }

}