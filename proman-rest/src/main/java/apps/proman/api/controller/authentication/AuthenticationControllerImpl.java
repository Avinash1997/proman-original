/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: AuthenticationControllerImpl.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package apps.proman.api.controller.authentication;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import apps.proman.api.model.AuthorizedUserResponse;
import apps.proman.api.model.RoleDetailsType;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.api.controller.ext.ResponseBuilder;
import apps.proman.api.controller.ext.SecuredResource;
import apps.proman.api.controller.provider.BasicAuthDecoder;
import apps.proman.api.controller.provider.BearerAuthDecoder;
import apps.proman.service.user.domain.AuthenticationService;
import apps.proman.service.user.model.AuthorizedUser;
import apps.proman.service.user.model.UserRole;

/**
 * Resource implementation for {@link AuthenticationController}.
 */
@RestController
public class AuthenticationControllerImpl extends SecuredResource implements AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthorizedUserResponse> login(@RequestHeader final String authorization) throws ApplicationException {
        final BasicAuthDecoder basicAuthDecoder = new BasicAuthDecoder(authorization);
        final AuthorizedUser authorizedUser = authenticationService.authenticate(getRequestContext(), basicAuthDecoder.getUsername(), basicAuthDecoder.getPassword());
        return new ResponseBuilder<AuthorizedUserResponse>(HttpStatus.OK).payload(authorizedUserTransform.apply(authorizedUser))
                    .accessToken(authorizedUser.getAccessToken()).build();
    }

    @Override
    public void logout(@RequestHeader final String authorization) throws ApplicationException {
        final BearerAuthDecoder authDecoder = new BearerAuthDecoder(authorization);
        authenticationService.logout(getRequestContext(), authDecoder.getAccessToken());
    }

    private Function<AuthorizedUser, AuthorizedUserResponse> authorizedUserTransform =
            authorizedUser ->
                    new AuthorizedUserResponse()
                            .id(UUID.fromString(authorizedUser.getId()))
                            .firstName(authorizedUser.getFirstName()).lastName(authorizedUser.getLastName())
                            .emailAddress(authorizedUser.getEmailAddress()).mobilePhone(authorizedUser.getMobilePhoneNumber())
                            .lastLoginTime(authorizedUser.getLastLoginTime())
                            .status(authorizedUser.getStatus().name())
                            .role(new RoleDetailsType().id(authorizedUser.getRole().getUuid()).name(authorizedUser.getRole().getName()));

}