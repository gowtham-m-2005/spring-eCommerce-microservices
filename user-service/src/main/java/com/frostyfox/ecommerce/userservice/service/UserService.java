package com.frostyfox.ecommerce.userservice.service;

import com.frostyfox.ecommerce.userservice.Dto.AddressDto;
import com.frostyfox.ecommerce.userservice.Dto.UserRequest;
import com.frostyfox.ecommerce.userservice.Dto.UserResponse;
import com.frostyfox.ecommerce.userservice.dao.UserDao;
import com.frostyfox.ecommerce.userservice.models.Address;
import com.frostyfox.ecommerce.userservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public ResponseEntity<String> addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);

        userDao.save(user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(String.valueOf(userRequest.getPhoneNumber()));

        if(userRequest.getAddressDto() != null){
            Address address = new Address();
            address.setCity(userRequest.getAddressDto().getCity());
            address.setCountry(userRequest.getAddressDto().getCountry());
            address.setStreet(userRequest.getAddressDto().getStreet());
            address.setZip(userRequest.getAddressDto().getZip());
            address.setState(userRequest.getAddressDto().getState());
            user.setAddress(address);
        }
    }

    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userDao.findAll();
        List<UserResponse> userResponses = userDao.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUserById(Integer id) {
        return userDao.findById(id)
                .map(user -> ResponseEntity.ok(mapToUserResponse(user)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<UserResponse> updateUser(Integer id, UserRequest userRequest) {
        return userDao.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, userRequest);


                    User savedUser = userDao.save(existingUser);
                    return ResponseEntity.ok(mapToUserResponse(savedUser));
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        userResponse.setUserRole(user.getUserRole());

        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();

            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZip(user.getAddress().getZip());
            addressDto.setCountry(user.getAddress().getCountry());
            userResponse.setAddressDto(addressDto);
        }

        return userResponse;
    }

}
