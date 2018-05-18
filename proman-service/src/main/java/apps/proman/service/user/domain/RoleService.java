package apps.proman.service.user.domain;

import javax.validation.constraints.NotNull;

import apps.proman.service.common.data.RequestContext;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.entity.RoleEntity;
import apps.proman.service.user.entity.UserEntity;

public interface RoleService {

    RoleEntity findUserByUuid(@NotNull RequestContext requestContext, @NotNull Integer roleUuid) throws ApplicationException;

}