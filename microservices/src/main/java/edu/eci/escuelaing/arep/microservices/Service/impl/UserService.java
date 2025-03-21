package edu.eci.escuelaing.arep.microservices.Service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.escuelaing.arep.microservices.DTO.UserDTO;
import edu.eci.escuelaing.arep.microservices.Model.User;
import edu.eci.escuelaing.arep.microservices.Repository.UserRepository;
import edu.eci.escuelaing.arep.microservices.Service.UserServiceInterface;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userReturn = new ArrayList<>();
        for (User u: users){
            userReturn.add(toDTO(u));
        }
        return userReturn;
        
    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return toDTO(user.get());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        userRepository.save(user);
        return toDTO(user);
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    public User toEntity(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword());
    }
    

    
}
