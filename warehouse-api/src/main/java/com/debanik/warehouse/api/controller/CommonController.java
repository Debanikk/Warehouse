package com.debanik.warehouse.api.controller;

import com.debanik.warehouse.api.model.UserDetailsResponse;
import com.debanik.warehouse.service.business.UserAdminBusinessService;
import com.debanik.warehouse.service.entity.UserEntity;
import com.debanik.warehouse.service.exception.AuthorizationFailedException;
import com.debanik.warehouse.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {

    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("userId") final String userUuid,
                                                       @RequestHeader("authorization") final String authorization) throws UserNotFoundException, AuthorizationFailedException {
        final UserEntity userEntity = userAdminBusinessService.getUser(userUuid, authorization);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).aboutMe(userEntity.getAboutMe())
                .contactNumber(userEntity.getContactNumber()).country(userEntity.getCountry())
                .dob(userEntity.getDob().toString()).emailAddress(userEntity.getEmail());

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

}
