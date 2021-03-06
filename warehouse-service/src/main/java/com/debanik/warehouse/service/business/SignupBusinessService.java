package com.debanik.warehouse.service.business;

import com.debanik.warehouse.service.dao.UserDao;
import com.debanik.warehouse.service.entity.UserEntity;
import com.debanik.warehouse.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity) throws SignUpRestrictedException {
        UserEntity e1=userDao.getUserByEmail(userEntity.getEmail());
        UserEntity e2=userDao.getUserByUserName(userEntity.getUserName());
        if(e1!=null) {
            throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId");
        }
        if(e2!=null){
            throw new SignUpRestrictedException("SGR-001", "Try any other Username, this Username has already been taken");
        }

        String[] encryptedText=cryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setSalt(encryptedText[0]);
        userEntity.setPassword(encryptedText[1]);

        return userDao.createUser(userEntity);
    }

}
