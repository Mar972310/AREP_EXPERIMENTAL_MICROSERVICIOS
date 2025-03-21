package edu.eci.escuelaing.arep.microservices.Service.impl;

import edu.eci.escuelaing.arep.microservices.DTO.PostDTO;
import edu.eci.escuelaing.arep.microservices.DTO.StreamDTO;
import edu.eci.escuelaing.arep.microservices.Model.Post;
import edu.eci.escuelaing.arep.microservices.Model.Stream;
import edu.eci.escuelaing.arep.microservices.Repository.StreamRepository;
import edu.eci.escuelaing.arep.microservices.Service.StreamServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StreamService implements StreamServiceInterface {

    @Autowired
    private StreamRepository streamRepository;
    @Autowired
    private PostService postService;


    
    @Override
    public StreamDTO getStream() {
        List<Stream> existingStream = streamRepository.findAll();
        if (existingStream.isEmpty()) {
            StreamDTO stream = new StreamDTO();
            stream.setId(1L);
            stream.setName("hilo");
            stream.setPosts(new ArrayList<PostDTO>());
            Stream newStream = toEntity(stream);
            streamRepository.save(newStream);
        }
        Stream stream = (streamRepository.findAll()).get(0);
        return toDTO(stream);
    }


    @Override
    public StreamDTO addPost(PostDTO post) {
        Stream stream = streamRepository.findById(1L).get();
        PostDTO newPost = postService.createPost(post);
        List<Post> posts = stream.getPosts();
        posts.add(postService.toEntity(newPost));
        streamRepository.save(stream);
        return toDTO(stream);
    }

    @Override
    public boolean deletePost(Long id) {
        Stream stream = streamRepository.findById(1L).orElseThrow(() -> new RuntimeException("Stream not found"));
        stream.getPosts().removeIf(post -> post.getId().equals(id));
        streamRepository.save(stream);
        return true;
    }
    
    private StreamDTO toDTO(Stream stream) {
        List<PostDTO> post = new ArrayList<>();
        for(Post p:stream.getPosts() ){
            post.add(postService.toDTO(p));
        }
        return new StreamDTO(stream.getId(), stream.getName(), post);

    }
    
    private Stream toEntity(StreamDTO streamDTO) {
        List<Post> post = new ArrayList<>();
        for(PostDTO p: streamDTO.getPosts() ){
            post.add(postService.toEntity(p));
        }
        Stream stream = new Stream();
        stream.setId(streamDTO.getId());
        stream.setName(streamDTO.getName());
        stream.setPosts(post);
        return stream;
    }
}
