package com.debanik.warehouse.service.business;

import com.debanik.warehouse.service.dao.UserAuthDao;
import com.debanik.warehouse.service.dao.UserDao;
import com.debanik.warehouse.service.entity.UserAuthEntity;
import com.debanik.warehouse.service.entity.UserEntity;
import com.debanik.warehouse.service.exception.AuthorizationFailedException;
import com.debanik.warehouse.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAdminBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthDao userAuthDao;

    public UserEntity getUser(final String userUuid, final String authorization) throws UserNotFoundException, AuthorizationFailedException{
        UserEntity userEntity;
        UserAuthEntity userAuthTokenEntity = userAuthDao.getUserAuthEntity(authorization);
        if(userAuthTokenEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        if(userAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        else {
            userEntity = userDao.getUser(userUuid);
            if (userEntity == null) {
                throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
            }

        }

        return userEntity;
    }
}
