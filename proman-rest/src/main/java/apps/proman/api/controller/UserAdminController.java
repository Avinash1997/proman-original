package apps.proman.api.controller;

import static apps.proman.api.controller.transformer.UserTransformer.toCreateUserResponse;
import static apps.proman.api.controller.transformer.UserTransformer.toEntity;
import static apps.proman.api.controller.transformer.UserTransformer.toUserDetailsResponse;
import static apps.proman.api.data.ResourceConstants.BASE_URL_USERS;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apps.proman.api.controller.ext.ResponseBuilder;
import apps.proman.api.controller.ext.SecuredController;
import apps.proman.api.model.CreateUserRequest;
import apps.proman.api.model.CreateUserResponse;
import apps.proman.api.model.UserDetailsResponse;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.domain.UserService;
import apps.proman.service.user.entity.UserEntity;

@RestController
@RequestMapping(path = BASE_URL_USERS)
public class UserAdminController extends SecuredController  {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET, path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@RequestHeader final String authorization, @PathVariable("id") final String userUuid)
            throws ApplicationException {

        final UserEntity userEntity = userService.findUserByUuid(getRequestContext(), userUuid);
        return new ResponseBuilder<UserDetailsResponse>(HttpStatus.OK)
                .payload(toUserDetailsResponse().apply(userEntity)).build();
    }

    @RequestMapping(method = POST, path = "/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateUserResponse> createUser(@RequestHeader final String authorization, @ModelAttribute final CreateUserRequest newUserRequest) throws ApplicationException {

        final UserEntity createdUser = userService.createUser(getRequestContext(), toEntity().apply(newUserRequest));
        return new ResponseBuilder<CreateUserResponse>(HttpStatus.CREATED).payload(toCreateUserResponse().apply(createdUser)).build();
    }

}
