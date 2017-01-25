/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase.model;

import dataBase.annotations.database;
import dataBase.interfaces.DataBaseEntity;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author root
 */
public class User implements DataBaseEntity, Serializable {
   
    private final Integer id;
    
    private final String nickname;
    
    private final String password;
    
    private final String email;
    
    private final String pathImage;

    public User(final Integer id, final String nickname, final String password, final String email, final String pathImage) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.pathImage = pathImage;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.nickname);
        hash = 37 * hash + Objects.hashCode(this.password);
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.pathImage);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.nickname, other.nickname)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.pathImage, other.pathImage)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    @database(key = true)
    public Integer getId() {
        return id;
    }

    @database
    public String getNickname() {
        return nickname;
    }

    @database
    public String getPassword() {
        return password;
    }

    @database
    public String getEmail() {
        return email;
    }

    @database
    public String getPathImage() {
        return pathImage;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nickname=" + nickname + ", password=" + password + ", email=" + email + ", pathImage=" + pathImage + '}';
    }

    public User withNickname(final String nickname) {
        return new User(this.id, nickname, this.password, this.email, this.pathImage);
    }

    public User withPassword(final String password) {
        return new User(this.id, this.nickname, password, this.email, this.pathImage);
    }

    public User withEmail(final String email) {
        return new User(this.id, this.nickname, this.password, email, this.pathImage);
    }

    public User withPathImage(final String pathImage) {
        return new User(this.id, this.nickname, this.password, this.email, pathImage);
    }
}
