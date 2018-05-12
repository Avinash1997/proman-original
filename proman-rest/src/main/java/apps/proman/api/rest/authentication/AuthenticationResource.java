/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: AuthenticationResource.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package apps.proman.api.rest.authentication;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import apps.proman.service.common.exception.ApplicationException;
import apps.proman.api.rest.ext.ResourceConstants;

/**
 * Interface that defines Authentication related RESTful endpoints.
 */
@RequestMapping(path = ResourceConstants.USER_BASE_URL)
public interface AuthenticationResource {

    @RequestMapping(method = POST, path = "/authentication", produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity authenticate(@RequestHeader final String authorization) throws ApplicationException;

    @RequestMapping(method = POST, path = "/logout")
    void logout(@RequestHeader final String authorization) throws ApplicationException;

}