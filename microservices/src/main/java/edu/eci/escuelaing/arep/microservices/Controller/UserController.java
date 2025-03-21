package edu.eci.escuelaing.arep.microservices.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.escuelaing.arep.microservices.DTO.UserDTO;
import edu.eci.escuelaing.arep.microservices.Service.impl.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(){
        try {
            List<UserDTO> users =  userService.getAllUser();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not users");
        } 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@RequestParam Long id){
        try {
            UserDTO user = userService.getUser(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not found user");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?>  createUser(@RequestBody UserDTO user){
        try {
            UserDTO userR = userService.createUser(user);
            return ResponseEntity.ok().body(userR);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not created user");
        }
    }
 
}
