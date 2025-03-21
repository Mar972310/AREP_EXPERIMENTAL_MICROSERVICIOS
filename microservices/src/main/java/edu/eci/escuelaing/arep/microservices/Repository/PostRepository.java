package edu.eci.escuelaing.arep.microservices.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.eci.escuelaing.arep.microservices.Model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
