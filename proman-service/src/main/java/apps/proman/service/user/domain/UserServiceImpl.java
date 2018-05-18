/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.user.domain;

import static apps.proman.service.user.UserErrorCode.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.common.exception.EntityNotFoundException;
import apps.proman.service.user.UserErrorCode;
import apps.proman.service.user.dao.UserDao;
import apps.proman.service.user.entity.RoleEntity;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.UserStatus;
import apps.proman.service.user.provider.PasswordCryptographyProvider;

/**
 * Implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserEntity findUserByEmail(final String emailAddress) throws ApplicationException {

        final UserEntity userEntity = userDao.findByEmail(emailAddress);
        if (userEntity == null) {
            throw new EntityNotFoundException(USR_002, emailAddress);
        }
        return userEntity;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserEntity findUserByUuid(final String userUuid) throws ApplicationException {

        final UserEntity userEntity = userDao.findByUUID(UserEntity.class, userUuid);
        if (userEntity == null) {
            throw new EntityNotFoundException(USR_001, userUuid);
        }
        return userEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity createUser(final UserEntity newUser, final Integer roleUuid) throws ApplicationException {

        final UserEntity existingUser = userDao.findByEmail(newUser.getEmail());
        if(existingUser != null) {
            throw new ApplicationException(USR_009, newUser.getEmail());
        }

        newUser.setStatus(UserStatus.ACTIVE.getCode());
        newUser.setRole(roleService.findRoleByUuid(roleUuid));

        String password = newUser.getPassword();
        if(password == null) {
            password = "pr0manU$er@123";
        }

        final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
        newUser.setSalt(encryptedData[0]);
        newUser.setPassword(encryptedData[1]);

        return userDao.create(newUser);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public UserEntity createUser(final UserEntity newUser) throws ApplicationException {

        final UserEntity userEntity = userDao.findByEmail(newUser.getEmail());
        if(userEntity != null) {
            throw new ApplicationException(USR_009, newUser.getEmail());
        }

        newUser.setStatus(UserStatus.REGISTERED.getCode());

        final String[] encryptedData = passwordCryptographyProvider.encrypt(newUser.getPassword());
        userEntity.setPassword(encryptedData[0]);
        userEntity.setSalt(encryptedData[1]);

        return userDao.create(newUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(final String userUuid, final UserEntity updatedUser) throws ApplicationException {

        final UserEntity existingUserEntity = userDao.findByUUID(UserEntity.class, userUuid);
        if (existingUserEntity == null) {
            throw new EntityNotFoundException(USR_001, userUuid);
        }

        if(!existingUserEntity.getEmail().equalsIgnoreCase(updatedUser.getEmail()) && userDao.findByEmail(updatedUser.getEmail()) != null) {
            throw new ApplicationException(USR_008, updatedUser.getEmail());
        }

        userDao.update(updatedUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserStatus(final String userUuid, final UserStatus newUserStatus) throws ApplicationException {

        final UserEntity existingUserEntity = userDao.findByUUID(UserEntity.class, userUuid);
        if (existingUserEntity == null) {
            throw new EntityNotFoundException(USR_001, userUuid);
        }

        final UserStatus existingUserStatus = UserStatus.get(existingUserEntity.getStatus());
        if(existingUserStatus != newUserStatus) {
            existingUserEntity.setStatus(newUserStatus.getCode());
            userDao.update(existingUserEntity);
        }
    }

}