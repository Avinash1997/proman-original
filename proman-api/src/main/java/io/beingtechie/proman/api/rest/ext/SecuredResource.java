/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: RestResource.java
 * Date: Oct 6, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package io.beingtechie.proman.api.rest.ext;

import io.beingtechie.proman.api.common.data.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static io.beingtechie.proman.api.rest.ext.ResourceConstants.REQUEST_ATTR_REQUEST_CONTEXT;

/**
 * Base class to be inherited by all REST resources.
 */
public class SecuredResource {

    @Autowired
    private HttpServletRequest request;

    protected HttpServletRequest getRequest() {
        return request;
    }

    protected RequestContext getRequestContext() {
        return (RequestContext) request.getAttribute(REQUEST_ATTR_REQUEST_CONTEXT);
    }

}