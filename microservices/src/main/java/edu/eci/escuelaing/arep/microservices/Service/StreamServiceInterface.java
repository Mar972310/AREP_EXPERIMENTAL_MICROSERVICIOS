package edu.eci.escuelaing.arep.microservices.Service;

import edu.eci.escuelaing.arep.microservices.DTO.PostDTO;
import edu.eci.escuelaing.arep.microservices.DTO.StreamDTO;

public interface StreamServiceInterface {

    StreamDTO getStream();
    StreamDTO addPost(PostDTO post);
    boolean deletePost(Long id);
    
}
