package com.debanik.warehouse.service.dao;

import com.debanik.warehouse.service.entity.Warehouse;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class WarehouseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Warehouse createWareHouseEntry(Warehouse warehouse)
    {
        entityManager.persist(warehouse);
        return warehouse;
    }

    @Transactional
    public String editWarehouseContent(final String uuid, final Integer warehouseEntry)
    {
        try
        {

            Query query= entityManager.createQuery("update Warehouse w set w.product_capacity=:warehouseEntry where w.uuid=:uuid");
            query.setParameter("warehouseEntry", warehouseEntry);
            query.setParameter("uuid", uuid);
            query.executeUpdate();

        }catch (NoResultException nre)
        {
            return null;
        }
        return uuid;
    }

    public String getUserForWarehouse(final String uuid)
    {
        try
        {
            return entityManager.createNamedQuery("UserForWarehouseEntry",
                    Warehouse.class).setParameter("uuid", uuid).getSingleResult().getUser().getUuid();
        }catch (NoResultException nre)
        {
            return null;
        }

    }

    public List<Warehouse> getWarehouseEntryForProductId(String productId) {
        try
        {
            List warehouseEntry= entityManager.createQuery("SELECT w from Warehouse w where w.products.id =:productId", Warehouse.class).setParameter("productId", productId).getResultList();


            return warehouseEntry;
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }

    public Warehouse getWarehouseById(String uuid){
        try{
            return entityManager.createQuery("select w from Warehouse w where w.uuid=:uuid", Warehouse.class).setParameter("uuid", uuid).getSingleResult();
        }
        catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public void deleteWarehouseEntry(String warehouseId){
        Warehouse warehouseEntity = this.getWarehouseById(warehouseId);
        entityManager.remove(warehouseEntity);
    }
}




