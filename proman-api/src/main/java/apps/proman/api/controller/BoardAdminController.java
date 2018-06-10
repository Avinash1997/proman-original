package apps.proman.api.controller;

import static apps.proman.api.controller.transformer.BoardTransformer.toEntity;
import static apps.proman.api.controller.transformer.BoardTransformer.toResponse;
import static apps.proman.api.data.ResourceConstants.BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import apps.proman.api.controller.ext.ResponseBuilder;
import apps.proman.api.model.CreateBoardRequest;
import apps.proman.api.model.CreateBoardResponse;
import apps.proman.api.model.UpdateBoardRequest;
import apps.proman.service.board.business.BoardService;
import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.UserErrorCode;
import apps.proman.service.user.business.UserService;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.UserStatus;

@RestController
@RequestMapping(BASE_URL)
public class BoardAdminController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, path = "/boards", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateBoardResponse> createBoard(@RequestBody final CreateBoardRequest createBoardRequest) throws ApplicationException {

        UserEntity userEntity = userService.findUserByUuid(createBoardRequest.getOwnerId());
        if(userEntity == null) {
            throw new ApplicationException(UserErrorCode.USR_001, createBoardRequest.getOwnerId());
        }
        else if(UserStatus.INACTIVE.equals(UserStatus.get(userEntity.getStatus()))) {
            throw new ApplicationException(UserErrorCode.USR_008);
        }

        final BoardEntity boardEntity = toEntity(createBoardRequest);
        boardEntity.setOwner(userEntity);

        final BoardEntity createdBoard = boardService.createBoard(boardEntity);
        return new ResponseBuilder(HttpStatus.CREATED).payload(toResponse(createdBoard)).build();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/boards/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateBoard(@RequestBody final UpdateBoardRequest updateBoardRequest) throws ApplicationException {

        UserEntity userEntity = userService.findUserByUuid(updateBoardRequest.getOwnerId());
        if(userEntity == null) {
            throw new ApplicationException(UserErrorCode.USR_001, updateBoardRequest.getOwnerId());
        }
        else if(UserStatus.INACTIVE.equals(UserStatus.get(userEntity.getStatus()))) {
            throw new ApplicationException(UserErrorCode.USR_008);
        }

        final BoardEntity boardEntity = toEntity(updateBoardRequest);
        boardEntity.setOwner(userEntity);
        boardService.updateBoard(boardEntity);

        return new ResponseBuilder(HttpStatus.OK).build();
    }

}
