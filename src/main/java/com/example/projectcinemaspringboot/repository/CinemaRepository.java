package com.example.projectcinemaspringboot.repository;

import com.example.projectcinemaspringboot.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Cinema findByName(String name);
}
