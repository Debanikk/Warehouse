package com.debanik.warehouse.service.business;

import com.debanik.warehouse.service.dao.WarehouseDao;
import com.debanik.warehouse.service.dao.ProductDao;
import com.debanik.warehouse.service.dao.UserAuthDao;
import com.debanik.warehouse.service.dao.UserDao;
import com.debanik.warehouse.service.entity.Warehouse;
import com.debanik.warehouse.service.entity.UserAuthEntity;
import com.debanik.warehouse.service.entity.UserEntity;
import com.debanik.warehouse.service.exception.WarehouseNotFoundException;
import com.debanik.warehouse.service.exception.AuthorizationFailedException;
import com.debanik.warehouse.service.exception.InvalidProductException;
import com.debanik.warehouse.service.exception.InvalidProductException;
import com.debanik.warehouse.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserAuthDao authTokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductService productService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Warehouse createWarehouseEntry(final Warehouse warehouseEntity, final String authenticationToken, final String productId) throws AuthorizationFailedException, InvalidProductException {
        final Warehouse createdWarehouseEntity;
        if (!productService.getProductById(productId)) {
            throw new InvalidProductException("PROD-001", "The product entered is invalid");
        }
        else{
            UserAuthEntity userAuthEntity = authTokenDao.getUserAuthEntity(authenticationToken);
            if (userAuthEntity == null) {
                throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
            } else {
                if (userAuthEntity.getLogoutAt() != null) {
                    throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to post a answer");
                } else {
                    warehouseEntity.setUser(userAuthEntity.getUser());
                    createdWarehouseEntity = warehouseDao.createWareHouseEntry(warehouseEntity);
                    return createdWarehouseEntity;
                }
            }
        }

    }

    public String editWarehouseContent(final String uuid, final Integer warehouseCapacity, final String authorization) throws AuthorizationFailedException, WarehouseNotFoundException {

        UserAuthEntity userAuthEntity=  authTokenDao.getUserAuthEntity(authorization);
        if(userAuthEntity== null)
        {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else if(userAuthEntity.getLogoutAt() != null)
        {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to edit an answer");
        }
        else if(!(userAuthEntity.getUser().getUuid().equals(warehouseDao.getUserForWarehouse(uuid))) && !userAuthEntity.getUser().getRole().equalsIgnoreCase("admin"))
        {
            throw new AuthorizationFailedException("ATHR-003", "Only the answer owner or admin can delete the answer");
        }
        else
        {

            if(warehouseDao.editWarehouseContent(uuid, warehouseCapacity)== null)
            {
                throw new WarehouseNotFoundException("WARH-001", "Entered warehouse uuid does not exist");
            }
        }
        return uuid;
    }

    public List<Warehouse> getWarehouseForProductId(String questionId, String authorization) throws AuthorizationFailedException, InvalidProductException {

        UserAuthEntity userAuthTokenEntity = authTokenDao.getUserAuthEntity(authorization);

        if(userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }else if(userAuthTokenEntity.getLogoutAt() != null)
        {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to delete an answer");
        }else {
            List warehouseEntries=  warehouseDao.getWarehouseEntryForProductId(questionId);
            if(warehouseEntries.isEmpty()){
                throw new InvalidProductException("PROD-001", "The product with entered uuid whose details are to be seen does not exist");
            }
            return warehouseEntries;
        }


    }

    public Warehouse deleteWarehouseEntry(final String warehouseId, final String authorization) throws AuthorizationFailedException, UserNotFoundException, WarehouseNotFoundException {

        UserAuthEntity userAuthEntity =  authTokenDao.getUserAuthEntity(authorization);
        Warehouse deletedWarehouseEntry = warehouseDao.getWarehouseById(warehouseId);
        UserEntity loggedInUser;

        if(deletedWarehouseEntry != null){
            if(userAuthEntity != null){
                loggedInUser = userAuthEntity.getUser();
                if(userAuthEntity.getLogoutAt() != null){
                    throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to delete an answer");
                }
                else{
                    if(deletedWarehouseEntry.getUser().getUuid().equals(loggedInUser.getUuid()) || loggedInUser.getRole().equals("admin")){
                        warehouseDao.deleteWarehouseEntry(warehouseId);
                    }
                    else{
                        throw new AuthorizationFailedException("ATHR-003","Only the answer owner or admin can delete the answer");
                    }
                }
            }
            else{
                throw new AuthorizationFailedException("ATHR-001","User has not signed in");
            }
        }
        else{
            throw new WarehouseNotFoundException("ANS-001","Entered answer uuid does not exist");
        }

        return deletedWarehouseEntry;

    }
}
