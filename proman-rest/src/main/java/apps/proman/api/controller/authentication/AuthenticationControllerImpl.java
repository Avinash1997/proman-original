/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: AuthenticationControllerImpl.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package apps.proman.api.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import apps.proman.service.common.exception.ApplicationException;
import apps.proman.api.controller.ext.ResponseBuilder;
import apps.proman.api.controller.ext.SecuredResource;
import apps.proman.api.controller.provider.BasicAuthDecoder;
import apps.proman.api.controller.provider.BearerAuthDecoder;
import apps.proman.service.user.domain.AuthenticationService;
import apps.proman.service.user.model.AuthorizedUser;

/**
 * Resource implementation for {@link AuthenticationController}.
 */
@RestController
public class AuthenticationControllerImpl extends SecuredResource implements AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthorizedUser> authenticate(@RequestHeader final String authorization) throws ApplicationException {
        final BasicAuthDecoder basicAuthDecoder = new BasicAuthDecoder(authorization);
        final AuthorizedUser authorizedUser = authenticationService.authenticate(getRequestContext(), basicAuthDecoder.getUsername(), basicAuthDecoder.getPassword());
        return new ResponseBuilder<AuthorizedUser>(HttpStatus.OK).payload(authorizedUser).accessToken(authorizedUser.getAccessToken()).build();
    }

    @Override
    public void logout(@RequestHeader final String authorization) throws ApplicationException {
        final BearerAuthDecoder authDecoder = new BearerAuthDecoder(authorization);
        authenticationService.logout(getRequestContext(), authDecoder.getAccessToken());
    }

}