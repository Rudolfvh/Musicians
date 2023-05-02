package org.example.dao;

import lombok.Cleanup;
import lombok.Getter;
import org.example.entity.Album;
import org.example.utils.SessionManager;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class AlbumDao implements Dao<Long, Album> {
    @Getter
    private static final AlbumDao Instance = new AlbumDao();

    @Override
    public Optional<Album> findById(Long id) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        var album = findById(id, session);
        session.getTransaction().commit();
        return album;
    }

    public Optional<Album> findById(Long id, Session session) {
        Album album = session.get(Album.class, id);
        return album != null
                ? Optional.of(album)
                : Optional.empty();
    }

    @Override
    public List<Album> findAll() {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        Query query = session.createQuery(" FROM " + Album.class.getSimpleName());
        session.getTransaction().commit();
        return (List<Album>) query.getResultList();
    }

    @Override
    public void delete(Long id) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        var album = findById(id, session);
        session.delete(album);
        session.getTransaction().commit();
    }

    @Override
    public void update(Album entity) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public Optional<Album> save(Album entity) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return Optional.of(entity);
    }
}
