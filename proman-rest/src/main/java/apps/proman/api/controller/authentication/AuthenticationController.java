/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: AuthenticationController.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package apps.proman.api.controller.authentication;

import static apps.proman.api.data.ResourceConstants.BASE_URL_AUTH;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import apps.proman.api.model.AuthorizedUserResponse;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.model.AuthorizedUser;

/**
 * Interface that defines Authentication related RESTful endpoints.
 */
@RequestMapping(path = BASE_URL_AUTH)
public interface AuthenticationController {

    @RequestMapping(method = POST, path = "/login", produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<AuthorizedUserResponse> login(@RequestHeader final String authorization) throws ApplicationException;

    @RequestMapping(method = POST, path = "/logout")
    void logout(@RequestHeader final String authorization) throws ApplicationException;

}