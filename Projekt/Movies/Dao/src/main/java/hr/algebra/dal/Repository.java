/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nix
 */
public interface Repository<T> {
    int createSingle(T entity) throws Exception;
    List<T> createMultiple(List<T> entities) throws Exception;
    void update(int id, T entity) throws Exception;
    void delete(int id) throws Exception;
    Optional<T> selectSingle(int id) throws Exception;
    List<T> selectAll() throws Exception;
    
    default void deleteAll() throws Exception {}
    
    default List<T> selectMultiple(int id) throws Exception {
        return new ArrayList<>();
    }
}
