package apps.proman.service.board.dao;

import javax.validation.constraints.NotNull;

import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.board.model.BoardStatus;
import apps.proman.service.common.dao.BaseDao;
import apps.proman.service.common.model.SearchResult;

public interface BoardDao extends BaseDao<BoardEntity> {

    SearchResult<BoardEntity> find(int page, int limit);

    SearchResult<BoardEntity> find(BoardStatus boardStatus, int page, int limit);

    BoardEntity findByName(@NotNull String boardName);

}
