package apps.proman.service.board.business;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import apps.proman.service.board.BoardErrorCode;
import apps.proman.service.board.dao.BoardDao;
import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.board.model.BoardStatus;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.common.exception.EntityNotFoundException;
import apps.proman.service.common.model.SearchResult;

@Service
public class BoardServiceImpl implements  BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    public SearchResult<BoardEntity> findBoards(int offset, int limit) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public BoardEntity findBoard(@NotNull String boardUuid) throws ApplicationException {
        BoardEntity boardEntity = boardDao.findByUUID(boardUuid);
        if(boardEntity == null) {
            throw new EntityNotFoundException(BoardErrorCode.BRD_001, boardUuid);
        }

        return boardEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BoardEntity createBoard(@NotNull BoardEntity newBoard) throws ApplicationException {

        if(boardDao.findByName(newBoard.getName()) != null) {
            throw new ApplicationException(BoardErrorCode.BRD_002, newBoard.getName());
        }

        return boardDao.create(newBoard);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBoard(@NotNull BoardEntity updatedBoard) throws ApplicationException {

    }

    @Override
    public void deleteBoard(@NotNull String boardUuid) throws ApplicationException {

    }

    @Override
    public void changeBoardStatus(@NotNull String boardUuid, @NotNull BoardStatus newBoardStatus) {

    }
}
