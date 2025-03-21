package edu.eci.escuelaing.arep.microservices.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.eci.escuelaing.arep.microservices.Model.Stream;

public interface StreamRepository extends JpaRepository<Stream, Long>{

    Optional<Stream> findByName(String name);
    
}
