package apps.proman.service.board.business;

import javax.validation.constraints.NotNull;

import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.board.model.BoardStatus;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.common.model.SearchResult;

public interface BoardService {

    SearchResult<BoardEntity> findBoards(int offset, int limit);

    SearchResult<BoardEntity> findBoards(BoardStatus userStatus, int offset, int limit);

    BoardEntity findBoard(@NotNull String boardUuid) throws ApplicationException ;

    BoardEntity createBoard(@NotNull BoardEntity newBoard) throws ApplicationException;

    void updateBoard(@NotNull BoardEntity updatedBoard) throws ApplicationException;

    void deleteBoard(@NotNull String boardUuid) throws ApplicationException;

    void changeBoardStatus(@NotNull String boardUuid, @NotNull BoardStatus newBoardStatus) ;

}
