package com.debanik.warehouse.api.controller;


import com.debanik.warehouse.api.model.*;
import com.debanik.warehouse.service.business.ProductService;
import com.debanik.warehouse.service.entity.Products;
import com.debanik.warehouse.service.exception.AuthorizationFailedException;

import com.debanik.warehouse.service.exception.InvalidProductException;
import com.debanik.warehouse.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/")
public class ProductController {


    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST, path="/product/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductResponse> createProduct(final ProductRequest productRequest ,@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {
        Products productEntity = new Products();
        productEntity.setUuid(UUID.randomUUID().toString());
        productEntity.setProduct_name(productRequest.getContent());
        productEntity.setTimeStamp(ZonedDateTime.now());

        final Products createdProductEntity;
        createdProductEntity = productService.createProduct(productEntity, authorization);
        ProductResponse productResponse = new ProductResponse().id(createdProductEntity.getUuid()).status("PRODUCT CREATED");
        return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/product/delete/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductDeleteResponse> deleteProduct(@PathVariable("productId") final String productId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidProductException {

        productService.deleteProduct(productId, authorization);
        ProductDeleteResponse prdDeleteResponse = new ProductDeleteResponse().id(productId).status("DELETED");
        return new ResponseEntity<ProductDeleteResponse>(prdDeleteResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/product/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<ProductDetailsResponse> getAllProducts(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UserNotFoundException {
        List<ProductDetailsResponse> allProdResponses= new ArrayList<>() ;
        List<Products> products= productService.getAllProducts(authorization);
        for(Products p: products)
        {
            allProdResponses.add(new ProductDetailsResponse().id(p.getUuid()).content(p.getProduct_name()));
        }
        return allProdResponses;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/product/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<ProductDetailsResponse> getAllQuestionByUser(@RequestHeader("authorization") final String authorization,  @PathVariable("userId") final String userUuid) throws AuthorizationFailedException, UserNotFoundException {
        List<ProductDetailsResponse> allProdResponses= new ArrayList<>() ;
        List<Products> products= productService.getAllProductsByUser(authorization,userUuid);
        for(Products p: products)
        {
            allProdResponses.add(new ProductDetailsResponse().id(p.getUuid()).content(p.getProduct_name()));
        }
        return allProdResponses;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/product/edit/{productId}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductEditResponse> editProductName(@RequestHeader("authorization") final String authorization, @PathVariable("productId")final String productId, @RequestBody ProductEditRequest productEditRequest) throws AuthorizationFailedException, InvalidProductException {
        ProductEditResponse questionEditResponse= new ProductEditResponse().id( productService.editProductName(productId,productEditRequest.getContent(),authorization)).status("Product name EDITED");
        return new ResponseEntity<ProductEditResponse>(questionEditResponse, HttpStatus.OK);
    }
}

