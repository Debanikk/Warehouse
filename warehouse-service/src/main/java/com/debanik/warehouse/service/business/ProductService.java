package com.debanik.warehouse.service.business;


import com.debanik.warehouse.service.dao.ProductDao;
import com.debanik.warehouse.service.dao.UserAuthDao;
import com.debanik.warehouse.service.dao.UserDao;
import com.debanik.warehouse.service.entity.Products;
import com.debanik.warehouse.service.entity.UserAuthEntity;
import com.debanik.warehouse.service.exception.AuthorizationFailedException;
import com.debanik.warehouse.service.entity.UserEntity;
import com.debanik.warehouse.service.exception.InvalidProductException;
import com.debanik.warehouse.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public Products createProduct(final Products productEntity, final String authenticationToken) throws AuthorizationFailedException {
        final Products createdProductEntity;
        UserAuthEntity userAuthEntity = userAuthDao.getUserAuthEntity(authenticationToken);
        if(userAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else{
            if(userAuthEntity.getLogoutAt() != null){
                throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to post a question");
            }
            else{
                productEntity.setUser(userAuthEntity.getUser());
                createdProductEntity = productDao.createProduct(productEntity);
            }

        }
        return createdProductEntity;
    }

    public void deleteProduct(String productId, final String authorizationToken) throws AuthorizationFailedException, InvalidProductException {
        UserAuthEntity userAuthTokenEntity = userAuthDao.getUserAuthEntity(authorizationToken);

        if(userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }else if(userAuthTokenEntity.getLogoutAt() != null)
        {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to delete an answer");
        }else {
            Products product = productDao.getProductById(productId);
            if(product==null){
                throw new InvalidProductException("PROD-001", "Entered product uuid does not exist");
            }else {
                UserEntity user = product.getUser();
                UserEntity loggedInUser = userAuthTokenEntity.getUser();

                if (user.getRole().equalsIgnoreCase("nonadmin") && user.getId() != loggedInUser.getId()) {
                    throw new AuthorizationFailedException("ATHR-003", "Only the product owner or admin can delete the product");
                }else {
                    productDao.deleteProduct(productId);
                }
            }


        }




    }

    public boolean getProductById(final String productId)
    {
        if(productDao.getProductById(productId)!=null)
        {
            return true;
        }

        return false;
    }
    public List<Products> getAllProducts(final String authorization) throws AuthorizationFailedException {
        UserAuthEntity userAuthEntity=  userAuthDao.getUserAuthEntity(authorization);
        if(userAuthEntity== null)
        {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else if(userAuthEntity.getLogoutAt() != null)
        {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.");
        }
        else {
            return productDao.getAllProducts();
        }
    }

    public List<Products> getAllProductsByUser(final String authorization , final String userUUId) throws AuthorizationFailedException, UserNotFoundException {
        UserAuthEntity userAuthEntity=  userAuthDao.getUserAuthEntity(authorization);
        if(userAuthEntity== null)
        {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else if(userAuthEntity.getLogoutAt() != null)
        {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.");
        }
        else{
            UserEntity userEntity=userDao.getUser(userUUId);
            if (userEntity== null)
            {
                throw new UserNotFoundException("USR-001","User with entered uuid to be deleted does not exist");
            }
            else
            {
                return productDao.getAllProductsByUserId(userEntity.getId());
            }
        }

    }

    public String editProductName(final String uuid, final String productName, final String authorization) throws AuthorizationFailedException, InvalidProductException {

        UserAuthEntity userAuthEntity=  userAuthDao.getUserAuthEntity(authorization);
        if(userAuthEntity== null)
        {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else if(userAuthEntity.getLogoutAt() != null)
        {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to delete an answer");
        }
        else if(!(userAuthEntity.getUser().getUuid().equals(productDao.getUserForProducts(uuid))) && !userAuthEntity.getUser().getRole().equalsIgnoreCase("admin"))
        {
            throw new AuthorizationFailedException("ATHR-003", "Only the question owner or admin can delete the question");
        }
        else
        {

            if(productDao.editProductName(uuid, productName)== null)
            {
                throw new InvalidProductException("PROD-001", "Entered product uuid does not exist");
            }
        }
        return uuid;
    }

}
