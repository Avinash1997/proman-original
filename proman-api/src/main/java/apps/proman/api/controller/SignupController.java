package apps.proman.api.controller;

import static apps.proman.api.controller.transformer.UserTransformer.toEntity;
import static apps.proman.api.data.ResourceConstants.BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apps.proman.api.controller.ext.ResponseBuilder;
import apps.proman.api.model.SignupUserRequest;
import apps.proman.api.model.SignupUserResponse;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.domain.UserService;
import apps.proman.service.user.entity.UserEntity;

@RestController
@RequestMapping(BASE_URL)
public class SignupController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = POST, path = "/signup", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(@ModelAttribute final SignupUserRequest signupUserRequest) throws ApplicationException {

        final UserEntity newUserEntity = toEntity(signupUserRequest);
        userService.createUser(newUserEntity);
        return new ResponseBuilder<SignupUserResponse>(HttpStatus.CREATED).build();
    }

}
