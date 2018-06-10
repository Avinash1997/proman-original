package apps.proman.api.controller;

import static apps.proman.api.controller.transformer.UserTransformer.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apps.proman.api.controller.ext.ResponseBuilder;
import apps.proman.api.exception.RestException;
import apps.proman.api.model.CreateUserRequest;
import apps.proman.api.model.CreateUserResponse;
import apps.proman.api.model.UserDetailsResponse;
import apps.proman.api.model.UserStatusType;
import apps.proman.api.model.UsersSummaryResponse;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.UserErrorCode;
import apps.proman.service.user.domain.UserService;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.SearchResult;
import apps.proman.service.user.model.UserStatus;

@RestController
@RequestMapping("/v1")
public class UserAdminController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = GET, path = "/users", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UsersSummaryResponse> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                         @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                         @RequestParam(value  ="status", required = false) String status) {

        final SearchResult<UserEntity> searchResult;
        if(StringUtils.isEmpty(status)) {
            searchResult = userService.findUsers(page, limit);
        }
        else {
            try{
                final UserStatusType userStatusType = UserStatusType.valueOf(status);
                searchResult = userService.findUsers(UserStatus.valueOf(userStatusType.name()), page, limit);
            }catch(IllegalArgumentException exc) {
                throw new RestException(UserErrorCode.USR_010, StringUtils.join(UserStatusType.values(),","));
            }
        }

        return new ResponseBuilder<UsersSummaryResponse>(HttpStatus.OK).payload(toUsersSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/users/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") final String userUuid)
            throws ApplicationException {

        final UserEntity userEntity = userService.findUserByUuid(userUuid);
        return new ResponseBuilder<UserDetailsResponse>(HttpStatus.OK)
                    .payload(toUserDetailsResponse(userEntity)).build();
    }

    @RequestMapping(method = POST, path = "/users", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody final CreateUserRequest newUserRequest) throws ApplicationException {

        final UserEntity newUserEntity = toEntity(newUserRequest);
        final UserEntity createdUser = userService.createUser(newUserEntity, newUserRequest.getRole().getId());
        return new ResponseBuilder<CreateUserResponse>(HttpStatus.CREATED).payload(toCreateUserResponse(createdUser)).build();
    }

}
