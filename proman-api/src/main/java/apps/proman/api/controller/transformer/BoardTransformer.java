package apps.proman.api.controller.transformer;

import java.util.UUID;

import apps.proman.api.model.BoardStatusType;
import apps.proman.api.model.CreateBoardRequest;
import apps.proman.api.model.CreateBoardResponse;
import apps.proman.api.model.UpdateBoardRequest;
import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.board.model.BoardStatus;

public final class BoardTransformer {

    private BoardTransformer() {
    }

    public static BoardEntity toEntity(final CreateBoardRequest boardRequest) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName(boardRequest.getName());
        boardEntity.setDescription(boardRequest.getDescription());
        return boardEntity;
    }

    public static BoardEntity toEntity(final UpdateBoardRequest boardRequest) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setUuid(boardRequest.getId().toString());
        boardEntity.setName(boardRequest.getName());
        boardEntity.setDescription(boardRequest.getDescription());
        return boardEntity;
    }

    public static CreateBoardResponse toResponse(final BoardEntity boardEntity) {

        return new CreateBoardResponse().id(UUID.fromString(boardEntity.getUuid()))
                .status(toStatus(boardEntity.getStatus()));
    }

    private static BoardStatusType toStatus(final int statusCode) {
        return BoardStatusType.valueOf(BoardStatus.get(statusCode).name());
    }

}
