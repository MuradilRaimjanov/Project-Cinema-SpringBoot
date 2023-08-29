package com.example.projectcinemaspringboot.repository;

import com.example.projectcinemaspringboot.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByName(String name);

    List<Room> findAllById(Long id);

}
