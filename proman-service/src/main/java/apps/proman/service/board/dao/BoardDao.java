package apps.proman.service.board.dao;

import javax.validation.constraints.NotNull;

import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.common.dao.BaseDao;

public interface BoardDao extends BaseDao<BoardEntity> {

    BoardEntity findByName(@NotNull String boardName);

}
