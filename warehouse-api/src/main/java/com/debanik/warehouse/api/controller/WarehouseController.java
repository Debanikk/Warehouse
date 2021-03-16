package com.debanik.warehouse.api.controller;


import com.debanik.warehouse.api.model.*;
import com.debanik.warehouse.service.business.WarehouseService;
import com.debanik.warehouse.service.business.AuthenticationService;
import com.debanik.warehouse.service.business.ProductService;
import com.debanik.warehouse.service.business.ProductService;
import com.debanik.warehouse.service.business.WarehouseService;
import com.debanik.warehouse.service.entity.Warehouse;
import com.debanik.warehouse.service.exception.WarehouseNotFoundException;
import com.debanik.warehouse.service.exception.AuthorizationFailedException;
import com.debanik.warehouse.service.exception.InvalidProductException;
import com.debanik.warehouse.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, path="/product/{productId}/warehouse/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WarehouseResponse> createWarehouseEntry(final WarehouseRequest warehouseRequest ,@PathVariable("productId") final String productId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidProductException {
        Warehouse warehouseEntity = new Warehouse();
        warehouseEntity.setUuid(UUID.randomUUID().toString());
        warehouseEntity.setProduct_capacity(Integer.parseInt(warehouseRequest.getCapacity()));
        warehouseEntity.setTimeStamp(ZonedDateTime.now());

        final Warehouse createdWarehouseEntity;
        createdWarehouseEntity = warehouseService.createWarehouseEntry(warehouseEntity, authorization,productId);
        WarehouseResponse warehouseResponse = new WarehouseResponse().id(createdWarehouseEntity.getUuid()).status("Warehouse CREATED");
        return new ResponseEntity<WarehouseResponse>(warehouseResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/warehouse/edit/{warehouseId}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WarehouseEditResponse> editWarehouseCapacity(@RequestHeader("authorization") final String authorization, @PathVariable("warehouseId") final String warehouseId, @RequestBody WarehouseEditRequest warehouseEditRequest) throws AuthorizationFailedException, UserNotFoundException, WarehouseNotFoundException {

        WarehouseEditResponse warehouseEditResponse= new WarehouseEditResponse().id( warehouseService.editWarehouseContent(warehouseId, Integer.parseInt(warehouseEditRequest.getCapacity()),authorization)).status("Warehouse capacity EDITED");
        return new ResponseEntity<WarehouseEditResponse>(warehouseEditResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "warehouse/all/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WarehouseDetailsResponse> getAllWarehouseEntriesToProduct(@RequestHeader("authorization") final String authorization, @PathVariable("productId") final String productId) throws AuthorizationFailedException, InvalidProductException {

        List<Warehouse> warehouseEntries=warehouseService.getWarehouseForProductId(productId, authorization);
        String uuid=productId;
        String warehouseCapacity="";
        String productContent="";


        for (Warehouse warehouse:warehouseEntries) {
            warehouseCapacity += warehouseCapacity + warehouse.getProduct_capacity();
            productContent = warehouse.getProducts().getProduct_name();
        }
        WarehouseDetailsResponse warehouseDetailsResponse= new WarehouseDetailsResponse();
        warehouseDetailsResponse.setId(uuid);
        warehouseDetailsResponse.setProductName(productContent);
        warehouseDetailsResponse.setWarehouseCapacity(warehouseCapacity);


        return new ResponseEntity<WarehouseDetailsResponse>(warehouseDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path="/warehouse/delete/{warehouseId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WarehouseDeleteResponse> deleteWarehouseEntry(@RequestHeader("authorization") final String authorizationToken, @PathVariable("warehouseId") final String warehouseId) throws UserNotFoundException, AuthorizationFailedException, WarehouseNotFoundException {
        WarehouseDeleteResponse warehouseDeleteResponse = null;

        Warehouse warehouse = warehouseService.deleteWarehouseEntry(warehouseId, authorizationToken);
        warehouseDeleteResponse = new WarehouseDeleteResponse().id(warehouse.getUuid()).status("WAREHOUSE ENTRY DELETED");

        return new ResponseEntity<WarehouseDeleteResponse>(warehouseDeleteResponse,HttpStatus.OK);
    }



}
