package apps.proman.service.board.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

import apps.proman.service.board.entity.BoardEntity;
import apps.proman.service.board.model.BoardStatus;
import apps.proman.service.common.dao.BaseDaoImpl;
import apps.proman.service.common.model.SearchResult;

@Repository
public class BoardDaoImpl extends BaseDaoImpl<BoardEntity> implements  BoardDao {

    @Override
    public SearchResult<BoardEntity> find(int page, int limit) {
        final int totalCount = entityManager.createNamedQuery(BoardEntity.COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<BoardEntity> payload = entityManager.createNamedQuery(BoardEntity.BY_ALL, BoardEntity.class).setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<BoardEntity> find(BoardStatus boardStatus, int page, int limit) {
        final int totalCount = entityManager.createNamedQuery(BoardEntity.COUNT_BY_STATUS, Long.class)
                .setParameter("status", boardStatus.getCode())
                .getSingleResult().intValue();
        final List<BoardEntity> payload = entityManager.createNamedQuery(BoardEntity.BY_STATUS, BoardEntity.class)
                .setParameter("status", boardStatus.getCode())
                .setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

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
