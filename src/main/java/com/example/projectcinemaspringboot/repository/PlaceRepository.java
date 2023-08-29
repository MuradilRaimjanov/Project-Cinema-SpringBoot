package com.example.projectcinemaspringboot.repository;

import com.example.projectcinemaspringboot.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findAllById(Long id);
}
