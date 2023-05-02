package org.example.dao;

import lombok.Cleanup;
import lombok.Getter;
import org.example.entity.Artist;
import org.example.utils.SessionManager;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class ArtistDao implements Dao<Long, Artist> {
    @Getter
    private static final ArtistDao Instance = new ArtistDao();

    @Override
    public Optional<Artist> findById(Long id) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        var artist = findById(id, session);
        session.getTransaction().commit();
        return artist;
    }

    public Optional<Artist> findById(Long id, Session session) {
        Artist artist = session.get(Artist.class, id);
        return artist != null
                ? Optional.of(artist)
                : Optional.empty();
    }

    @Override
    public List<Artist> findAll() {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        Query query = session.createQuery(" FROM " + Artist.class.getSimpleName());
        session.getTransaction().commit();
        return (List<Artist>) query.getResultList();
    }

    @Override
    public void delete(Long id) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        var artist = findById(id, session);
        session.delete(artist);
        session.getTransaction().commit();
    }

    @Override
    public void update(Artist entity) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public Optional<Artist> save(Artist entity) {
        @Cleanup var session = SessionManager.get();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return Optional.of(entity);
    }
}
