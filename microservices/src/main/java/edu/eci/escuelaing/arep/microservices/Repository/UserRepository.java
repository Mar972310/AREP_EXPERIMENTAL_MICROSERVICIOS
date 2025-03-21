package edu.eci.escuelaing.arep.microservices.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.eci.escuelaing.arep.microservices.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
