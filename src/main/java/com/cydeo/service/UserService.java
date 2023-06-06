package com.cydeo.service;


import com.cydeo.dto.UserDTO;
import com.cydeo.exception.TicketingProjectRestException;

import java.util.List;

public interface UserService  {

    UserDTO findByUserName(String username);
    List<UserDTO> listAllUsers();
    void save(UserDTO user);
//    void deleteByUserName(String username);
    UserDTO update(UserDTO user);
    void delete(String username) throws TicketingProjectRestException;
    List<UserDTO> listAllByRole(String role);

}
