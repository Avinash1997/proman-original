/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: BaseDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.common.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.persistence.EntityManager;

import apps.proman.service.common.entity.Entity;

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
    public E findById(final Class<? extends Entity> entityClass, final Object id) {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
        return (E) getEntityManager().find(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findByUUID(final Class<? extends Entity> entityClass, final Object uuid) {
        return (E) getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName()
                + " e WHERE e.uuid = :uuid", entityClass).setParameter("uuid", uuid).getSingleResult();
    }

    protected abstract EntityManager getEntityManager();

}