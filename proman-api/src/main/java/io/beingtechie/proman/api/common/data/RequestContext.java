/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: RequestContext.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.common.data;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Object containing request context information.
 */
public interface RequestContext extends Serializable {

    void setRequestId(String requestId);

    String getRequestId();

    void setRequestTime(ZonedDateTime requestTime);

    ZonedDateTime getRequestTime();

    void setClientId(String clienId);

    String getClientId();

    void setClientIpAddress(String ipAddress);

    String getClientIpAddress();

    void setUserId(String userId);

    String getUserId();

    void setAccessedResource(String requestedResource);

    String getAccessedResource();

}