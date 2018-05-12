/* 
 * Copyright 2018-2019, https://beingtechie.io.
 * 
 * File: BaseDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
*/
package apps.proman.service.common.dao;

/**
 * TODO: Provide javadoc
 */
public interface BaseDao<E> {

    E create(E e);

    E update(E e);

    E findById(final E e, final Long id);

    E findByUUID(final E e, final String uuid);

}