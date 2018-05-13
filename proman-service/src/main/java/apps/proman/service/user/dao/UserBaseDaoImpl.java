/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserBaseDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import apps.proman.service.common.dao.BaseDaoImpl;
import apps.proman.service.common.entity.Entity;

/**
 * Base DAO abstraction for user module.
 */
public class UserBaseDaoImpl<E extends Entity> extends BaseDaoImpl<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
