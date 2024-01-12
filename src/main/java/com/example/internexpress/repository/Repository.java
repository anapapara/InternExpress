package com.example.internexpress.repository;

import com.example.internexpress.domain.Entity;
import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    /**
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException if id is null.
     */
    Optional<E> findOne(ID id);

    /**
     * @return all entities
     */
    ArrayList<E> findAll();

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws IllegalArgumentException if the given entity is null.     *
     */
    Optional<E> save(E entity);


    /**
     * removes the entity with the specified id
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional<E> delete(ID id);

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated,
     * otherwise  returns the entity  - (e.g id does not exist).
     * @throws IllegalArgumentException if the given entity is null.
     */
    Optional<E> update(E entity);

    default Optional<E> findUser(String email) {
        throw new UnsupportedOperationException();
    }

    default Optional<E> findloggedUser(String email, String password) {
        throw new UnsupportedOperationException();
    }

    default List<E> findByDomain(String domain) {
        throw new UnsupportedOperationException();
    }

    default List<Internship> findByCreator(Long userId) {
        throw new UnsupportedOperationException();
    }

    default void saveAppliance(E entity, User user) {
        throw new UnsupportedOperationException();
    }

    //default List<User> findAllApplicants(Long internshipId){throw new UnsupportedOperationException();}

    default void changeStatus(Internship entity, User user, String status) {throw new UnsupportedOperationException();}

    default List<User> getApplicants(List<Long>userId) {throw new UnsupportedOperationException();}

    default String getApplianceStatus(Long userId, ID internshipId){throw new UnsupportedOperationException();}
}
