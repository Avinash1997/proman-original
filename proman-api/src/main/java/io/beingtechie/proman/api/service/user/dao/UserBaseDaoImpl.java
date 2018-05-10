/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserBaseDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.beingtechie.proman.api.common.dao.BaseDaoImpl;
import io.beingtechie.proman.api.common.entity.Entity;

/**
 * Base DAO abstraction for user module.
 */
public class UserBaseDaoImpl<E extends Entity> extends BaseDaoImpl<E> {

    @PersistenceContext(unitName = "persistence.user")
    protected EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
