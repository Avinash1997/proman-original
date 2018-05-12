/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: AuthenticationResourceImpl.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package apps.proman.api.rest.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import apps.proman.service.common.exception.ApplicationException;
import apps.proman.api.rest.ext.ResponseBuilder;
import apps.proman.api.rest.ext.SecuredResource;
import apps.proman.api.rest.provider.BasicAuthDecoder;
import apps.proman.api.rest.provider.BearerAuthDecoder;
import apps.proman.service.user.domain.AuthenticationService;

/**
 * Resource implementation for {@link AuthenticationResource}.
 */
@RestController
public class AuthenticationResourceImpl extends SecuredResource implements AuthenticationResource {

    @Autowired
    private AuthenticationService authService;

    @Override
    public ResponseEntity authenticate(@RequestHeader final String authorization) throws ApplicationException {
        final BasicAuthDecoder basicAuthDecoder = new BasicAuthDecoder(authorization);
        final String accessToken = authService.authenticate(getRequestContext(), basicAuthDecoder.getUsername(), basicAuthDecoder.getPassword());
        return new ResponseBuilder(HttpStatus.OK).accessToken(accessToken).build();
    }

    @Override
    public void logout(@RequestHeader final String authorization) throws ApplicationException {
        final BearerAuthDecoder authDecoder = new BearerAuthDecoder(authorization);
        authService.logout(getRequestContext(), authDecoder.getAccessToken());
    }

}