package edu.eci.escuelaing.arep.microservices.Service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.escuelaing.arep.microservices.DTO.PostDTO;
import edu.eci.escuelaing.arep.microservices.Model.Post;
import edu.eci.escuelaing.arep.microservices.Repository.PostRepository;
import edu.eci.escuelaing.arep.microservices.Service.PostServiceInterface;

@Service
public class PostService implements PostServiceInterface {

    @Autowired
    private PostRepository postRepository;

    public PostDTO toDTO(Post post) {
        return new PostDTO(post.getId(), post.getMessage(), post.getUser(),post.getLikes());
    }

    public Post toEntity(PostDTO postDTO) {
        return new Post(postDTO.getId(), postDTO.getMessage(),postDTO.getLikes(), postDTO.getUser());
    }

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postReturn = new ArrayList<>();
        for (Post u: posts){
            postReturn.add(toDTO(u));
        }
        return postReturn;
    }

    @Override
    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            return toDTO(post);
        }
        return null;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        if(postDTO.getMessage().length() <= 140){
            Post post = toEntity(postDTO);
            Post savedPost = postRepository.save(post);
            return toDTO(savedPost);
        }
        return null;
       
    }

    @Override
    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PostDTO addLikes(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setLikes(post.getLikes()+1);
            Post savedPost = postRepository.save(post);
            return toDTO(savedPost);
        }
        return null;
    }
}
