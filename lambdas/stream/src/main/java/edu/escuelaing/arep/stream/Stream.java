/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.escuelaing.arep.stream;

import java.util.List;

/**
 *
 * @author maritzamonsalvebautista
 */
public class Stream {

    private Long id;
    private List<Long> posts;
    
    public Stream(){}
    
    public Stream(List<Long> posts){
        this.posts = posts;
    }
    
    public Stream(Long id,List<Long> posts){
        this.id = id;
        this.posts = posts;
    }
    
    public Long getId(){
        return id;
    }
    
    public List<Long> getPosts(){
        return posts;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public void setUsername(List<Long> posts){
        this.posts = posts;
    }
}
