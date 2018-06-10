package apps.proman.service.board.dao;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.common.dao.BaseDaoImpl;

@Repository
public class BoardDaoImpl extends BaseDaoImpl<BoardEntity> implements  BoardDao {

    @Override
    public BoardEntity findByName(@NotNull String boardName) {
        try {
            return entityManager.createNamedQuery(BoardEntity.BY_NAME, BoardEntity.class)
                    .setParameter("name", boardName).getSingleResult();
        }catch(NoResultException exc){
            return null;
        }
    }
}
