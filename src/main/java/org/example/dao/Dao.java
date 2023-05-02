package org.example.dao;

import lombok.Cleanup;
import org.example.utils.SessionManager;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    default void update(E entity) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    Optional<E> findById(K id);

    List<E> findAll();

    default void delete(K id) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        findById(id).ifPresent(session::delete);
        session.getTransaction().commit();
    }

    default Optional<E> save(E entity) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        session.save(entity);
        entity = (E) session.merge(entity);
        session.getTransaction().commit();
        return entity != null
                ? Optional.of(entity)
                : Optional.empty();
    }
}
