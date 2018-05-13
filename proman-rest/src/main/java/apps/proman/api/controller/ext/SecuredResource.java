/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: RestResource.java
 * Date: Oct 6, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package apps.proman.api.controller.ext;

import static apps.proman.api.data.ResourceConstants.REQUEST_ATTR_REQUEST_CONTEXT;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import apps.proman.service.common.data.RequestContext;

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