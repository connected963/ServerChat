/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import interfaces.dataBaseEntity;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author connected
 * @param <T>
 */
public interface persistence<T extends dataBaseEntity> {
 
    /**
     * Insert a new register of entity on database
     * 
     * @param entity
     * @return 
    */
    public Optional<T> insert(T entity);
    
    /**
     * Update a register of entity on database
     * 
     * @param entity
     * @return 
    */
    public Optional<T> update(T entity);
    
    /**
     * Remove register of entity on database
     * 
     * @param entity 
    */
    public void remove(T entity);
    
    /**
     * Find registers of entity on database
     * 
     * @param where
     * @return 
    */
    public Optional<List<T>> select(String where);
    
    /**
     * Find registers of entity on database 
     * @param dtoClass
     * @param where
     * @return 
    */
    public <O> Optional<List<O>> select(Class dtoClass, String where);
    
}
