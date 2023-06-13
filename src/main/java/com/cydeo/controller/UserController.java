package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.exception.TicketingProjectRestException;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


//for consuming APIs (no VIEW returned)
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @RolesAllowed({"Manager","Admin"})
    @Operation(summary = "GET - ALL USERS")
    public ResponseEntity<ResponseWrapper> getUsers(){
        return ResponseEntity.ok(new ResponseWrapper("ALL Users Retrieved", userService.listAllUsers(), HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    @Operation(summary = "GET - USERS BY USER-NAME")
    public ResponseEntity<ResponseWrapper> getUsersByUserName(@PathVariable("userName") String userName){
        return ResponseEntity.ok(new ResponseWrapper("Retrieved User by GIVEN User-Name", userService.findByUserName(userName), HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    @Operation(summary = "NEW USER")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("New User Created", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Admin")
    @Operation(summary = "UPDATE - USER")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){
        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("User -> " + user.getUserName() + " <- Updated", HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    @Operation(summary = "DELETE - USER")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userName") String userName) throws TicketingProjectRestException {
        userService.delete(userName);
        return ResponseEntity.ok(new ResponseWrapper("User -> " + userName + " <- Deleted", HttpStatus.OK));
    }

}
