package edu.eci.escuelaing.arep.microservices.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.escuelaing.arep.microservices.DTO.PostDTO;
import edu.eci.escuelaing.arep.microservices.DTO.StreamDTO;
import edu.eci.escuelaing.arep.microservices.Service.impl.StreamService;

@RestController
@RequestMapping("/api/v1/stream")
public class StreamController {

    @Autowired
    private StreamService streamService;


    @GetMapping
    public ResponseEntity<?> getStream(){
        try {
            StreamDTO streamDTO = streamService.getStream();
            return ResponseEntity.ok().body(streamDTO);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(203).body("Not found stream");
        }        
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody PostDTO post){
        try {
            StreamDTO streamDTO = streamService.addPost(post);
            return ResponseEntity.ok().body(streamDTO);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(203).body("No se pudo añadir");
        }        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try {
            boolean post = streamService.deletePost(id);
            return ResponseEntity.ok().body(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not created post");
        }
    }
}
