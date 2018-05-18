package apps.proman.service.user.domain;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apps.proman.service.common.data.RequestContext;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.dao.RoleDao;
import apps.proman.service.user.entity.RoleEntity;

@Service
public class RoleServiceImpl implements  RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleEntity findUserByUuid(@NotNull RequestContext requestContext, @NotNull Integer roleUuid) throws ApplicationException {
        return roleDao.findByUUID(RoleEntity.class, roleUuid);
    }
}
