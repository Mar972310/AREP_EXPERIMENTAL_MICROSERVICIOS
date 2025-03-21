/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.escuelaing.arep.user;

/**
 *
 * @author maritzamonsalvebautista
 */
public class User {
    
    private Long id;
    private String username;
    
    public User(){}
    
    public User(String username){
        this.username = username;
    }
    
    public User(Long id,String username){
        this.id = id;
        this.username = username;
    }
    
    public Long getId(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public void setUsername(String username){
        this.username = username;
        
    }
 
}
