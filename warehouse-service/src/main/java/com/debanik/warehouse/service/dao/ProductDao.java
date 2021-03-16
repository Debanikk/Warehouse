package com.debanik.warehouse.service.dao;

import com.debanik.warehouse.service.entity.Products;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Products createProduct(Products productEntity)
    {
        entityManager.persist(productEntity);
        return productEntity;
    }

    @Transactional
    public void deleteProduct(String productId) {
        Products products = this.getProductById(productId);
        entityManager.remove(products);
    }

    public Products getProductById( String productId)   {
        try {
            return entityManager.createNamedQuery("productsById",Products.class).
                    setParameter("productId", productId).getSingleResult();
        }catch (NoResultException nre)
        {
            return null;
        }

    }

    public List<Products> getAllProducts()
    {
        try
        {
            return entityManager.createNamedQuery("AllProducts",
                    Products.class).getResultList();
        }catch (NoResultException nre)
        {
            return null;
        }
    }

    public List<Products> getAllProductsByUserId(final Integer id)
    {
        try
        {
            return entityManager.createNamedQuery("AllProductsByUserId",
                    Products.class).setParameter("id", id).getResultList();
        }catch (NoResultException nre)
        {
            return null;
        }
    }

    @Transactional
    public String editProductName(final String uuid, final String prodName)
    {
        try
        {

            Query query= entityManager.createQuery("update Products p set p.product_name=:prodName where p.uuid=:uuid");
            query.setParameter("prodName", prodName);
            query.setParameter("uuid", uuid);
            query.executeUpdate();

        }catch (NoResultException nre)
        {
            return null;
        }
        return uuid;
    }

    public String getUserForProducts(final String uuid) {
        try {
            return entityManager.createNamedQuery("UserForProduct",
                    Products.class).setParameter("uuid", uuid).getSingleResult().getUser().getUuid();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
