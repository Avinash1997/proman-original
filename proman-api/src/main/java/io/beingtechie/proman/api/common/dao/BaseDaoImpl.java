/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: BaseDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.common.dao;

import javax.persistence.EntityManager;

import io.beingtechie.proman.api.common.entity.Entity;

/**
 * Generic DAO abstraction for all DAOs to inherit common functionality.
 */
public abstract class BaseDaoImpl<E extends Entity> implements BaseDao<E> {

    @Override
    public E create(E e) {
        getEntityManager().persist(e);
        return e;
    }

    @Override
    public E update(E e) {
        return getEntityManager().merge(e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findById(final E e, final Long id) {
        return (E) getEntityManager().find(e.getClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findByUUID(final E e, final String uuid) {
        return (E) getEntityManager().createNamedQuery("SELECT e FROM " + e.getClass().getSimpleName()
                + " WHERE e.uuid = :uuid", e.getClass()).setParameter("uuid", uuid).getSingleResult();
    }

    protected abstract EntityManager getEntityManager();

}