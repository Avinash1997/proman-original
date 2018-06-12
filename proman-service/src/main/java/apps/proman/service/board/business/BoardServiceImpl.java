package apps.proman.service.board.business;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
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
    public SearchResult<BoardEntity> findBoards(int page, int limit) {
        return boardDao.find(page, limit);
    }

    @Override
    public SearchResult<BoardEntity> findBoards(BoardStatus boardStatus, int page, int limit) {
        return boardDao.find(boardStatus, page, limit);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public BoardEntity findBoard(@NotNull String boardUuid) throws ApplicationException {

        final BoardEntity existingBoard = boardDao.findByUUID(boardUuid);
        if(existingBoard == null) {
            throw new EntityNotFoundException(BoardErrorCode.BRD_001, boardUuid);
        }

        return existingBoard;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BoardEntity createBoard(@NotNull BoardEntity newBoard) throws ApplicationException {

        if(boardDao.findByName(newBoard.getName()) != null) {
            throw new ApplicationException(BoardErrorCode.BRD_002, newBoard.getName());
        }

        newBoard.setStatus(BoardStatus.OPEN.getCode());
        return boardDao.create(newBoard);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBoard(final String boardUuid, final BoardEntity updatedBoard) throws ApplicationException {

        final BoardEntity existingBoard = boardDao.findByUUID(boardUuid);
        if(existingBoard == null) {
            throw new EntityNotFoundException(BoardErrorCode.BRD_001, boardUuid);
        }

        if(!existingBoard.getName().equalsIgnoreCase(updatedBoard.getName()) && boardDao.findByName(updatedBoard.getName()) != null) {
            throw new ApplicationException(BoardErrorCode.BRD_002, updatedBoard.getName());
        }

        if(StringUtils.isNotEmpty(updatedBoard.getName())) {
            existingBoard.setName(updatedBoard.getName());
        }

        if(StringUtils.isNotEmpty(updatedBoard.getDescription())) {
            existingBoard.setDescription(updatedBoard.getDescription());
        }

        if(updatedBoard.getOwner() != null) {
            existingBoard.setOwner(updatedBoard.getOwner());
        }

        boardDao.update(existingBoard);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBoard(@NotNull String boardUuid) throws ApplicationException {

        final BoardEntity existingBoard = boardDao.findByUUID(boardUuid);
        if(existingBoard == null) {
            throw new EntityNotFoundException(BoardErrorCode.BRD_001, boardUuid);
        }

        existingBoard.setStatus(BoardStatus.DELETED.getCode());
        boardDao.update(existingBoard);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeBoardStatus(@NotNull String boardUuid, @NotNull BoardStatus newBoardStatus) throws ApplicationException {

        final BoardEntity existingBoard = boardDao.findByUUID(boardUuid);
        if(existingBoard == null) {
            throw new EntityNotFoundException(BoardErrorCode.BRD_001, boardUuid);
        }

        if(existingBoard.getStatus() != newBoardStatus.getCode()) {
            existingBoard.setStatus(newBoardStatus.getCode());
            boardDao.update(existingBoard);
        }
    }

}
