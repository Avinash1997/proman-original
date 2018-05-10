/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: ResourceConstants.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package io.beingtechie.proman.api.rest.ext;

/**
 * TODO: Provide javadoc
 */
public interface ResourceConstants {

    String BASE_URL = "/";

    String BASE_URL_PATTERN = BASE_URL + "*";

    String USER_BASE_URL = "/user";

    String HEADER_ES_API_VERSION = "es-api-version";

    String HEADER_AUTHORIZATION = "authorization";

    String HEADER_CLIENT_ID = "client-id";

    String HEADER_CLIENT_IP_ADDRESS = "X-FORWARDED-FOR";

    String HEADER_REQUEST_ID = "request-id";

    String HEADER_ACCESS_TOKEN = "access-token";

    String REQUEST_ATTR_REQUEST_CONTEXT = "request-context";

}