package com.example.projectcinemaspringboot.repository;

import com.example.projectcinemaspringboot.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByName(String name);

    @Query("from Session s where s.movie.id = ?1")
    List<Session> findAllByMovieId(Long id);
}
