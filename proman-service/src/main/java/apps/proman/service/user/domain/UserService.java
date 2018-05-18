/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;

import javax.validation.constraints.NotNull;

import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.UserStatus;

/**
 * Interface for user related services.
 */
public interface UserService {

    UserEntity findUserByEmail(@NotNull String emailAddress) throws ApplicationException;

    UserEntity findUserByUuid(@NotNull String userUuid) throws ApplicationException;

    UserEntity createUser(@NotNull UserEntity newUser, @NotNull Integer roleUuid) throws ApplicationException;

    UserEntity createUser(@NotNull UserEntity newUser) throws ApplicationException;

    void updateUser(@NotNull String userUuid, @NotNull UserEntity updatedUser) throws ApplicationException;

    void updateUserStatus(@NotNull String userUuid, @NotNull UserStatus newUserStatus) throws ApplicationException;

}