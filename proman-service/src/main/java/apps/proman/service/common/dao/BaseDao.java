/* 
 * Copyright 2018-2019, https://beingtechie.io.
 * 
 * File: BaseDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
*/
package apps.proman.service.common.dao;

import apps.proman.service.common.entity.Entity;

/**
 * TODO: Provide javadoc
 */
public interface BaseDao<E> {

    E create(E e);

    E update(E e);

    E findById(Class<? extends Entity> entityClass, final Object id);

    E findByUUID(Class<? extends Entity> entityClass, final Object uuid);

}