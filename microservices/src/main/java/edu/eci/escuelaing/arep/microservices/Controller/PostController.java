package edu.eci.escuelaing.arep.microservices.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.escuelaing.arep.microservices.DTO.PostDTO;
import edu.eci.escuelaing.arep.microservices.Service.impl.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @GetMapping("/all")
    private ResponseEntity<?> allPosts(){
        try{
            List<PostDTO> posts = postService.getAllPost();
            return ResponseEntity.ok().body(posts);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Not found posts");
        }
    } 

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id){
        try {
            PostDTO post = postService.getPost(id);
            
            return ResponseEntity.ok().body(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not found post");
        }
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<?> addLike(@PathVariable Long id){
        try {
            PostDTO post = postService.addLikes(id);
            return ResponseEntity.ok().body(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not like add");
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO){
        try {
            PostDTO post = postService.createPost(postDTO);
            if(post == null){
                return ResponseEntity.badRequest().body("Mensaje muy largo");
            }
            return ResponseEntity.ok().body(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not created post");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try {
            boolean post = postService.deletePost(id);
            return ResponseEntity.ok().body(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not deleted post");
        }
    }
    
}
