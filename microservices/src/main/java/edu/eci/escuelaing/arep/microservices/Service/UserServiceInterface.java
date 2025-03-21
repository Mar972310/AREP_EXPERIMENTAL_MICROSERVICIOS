package edu.eci.escuelaing.arep.microservices.Service;

import java.util.List;

import edu.eci.escuelaing.arep.microservices.DTO.UserDTO;

public interface UserServiceInterface {

    List<UserDTO> getAllUser();
    UserDTO getUser(Long id);
    UserDTO createUser(UserDTO userDTO);
    
}
