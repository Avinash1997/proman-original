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
import javax.persistence.PersistenceContext;

import apps.proman.service.common.entity.Entity;

/**
 * Generic DAO abstraction for all DAOs to inherit common functionality.
 */
public class BaseDaoImpl<E extends Entity> implements BaseDao<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public E create(E e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    public E update(E e) {
        return entityManager.merge(e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findById(final Class<? extends Entity> entityClass, final Object id) {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
        return (E) entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findByUUID(final Class<? extends Entity> entityClass, final Object uuid) {
        return (E) entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName()
                + " e WHERE e.uuid = :uuid", entityClass).setParameter("uuid", uuid).getSingleResult();
    }

}