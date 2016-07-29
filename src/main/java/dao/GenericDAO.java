/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helper.persistenceHelper;
import interfaces.dataBaseEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author connected
 * @param <T>
 */
public class GenericDAO <T extends dataBaseEntity> {
    
    persistenceHelper<T> persistence;

    public GenericDAO(Class type) {
        persistence = new persistenceHelper<>(type);
    }
    
    public Optional<T> persist(T entity) {
        Optional<T> optionalEntity;
        if(Objects.isNull(entity.getId())) {
            optionalEntity = persistence.insert(entity);
        } else {
            optionalEntity = persistence.update(entity);
        }
        
        return optionalEntity;
    }
    
    public void delete(T entity) {
        persistence.remove(entity);
    }
    
    public Optional<List<T>> find(String where) {
        return persistence.select(where);
    }
    
    public <D> Optional<List<D>> find(String where, Class type) {
        return persistence.select(type, where);
    }
}
