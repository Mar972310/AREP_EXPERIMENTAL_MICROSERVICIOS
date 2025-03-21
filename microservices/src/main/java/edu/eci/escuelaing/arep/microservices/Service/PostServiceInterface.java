package edu.eci.escuelaing.arep.microservices.Service;

import java.util.List;
import edu.eci.escuelaing.arep.microservices.DTO.PostDTO;

public interface PostServiceInterface {

    List<PostDTO> getAllPost();
    PostDTO getPost(Long id);
    PostDTO createPost(PostDTO post);
    boolean deletePost(Long id);


}
