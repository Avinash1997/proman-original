/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: ExternalIdentifier.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.common.entity;

/**
 * Interface that represents External Identifiable.
 */
public interface ExternalIdentifier<K> {

    /**
     * @return the type safe external identifiable object.
     */
    K getUuid();

}