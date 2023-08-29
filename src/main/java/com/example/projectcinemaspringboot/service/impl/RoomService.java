package com.example.projectcinemaspringboot.service.impl;

import com.example.projectcinemaspringboot.exception.EntityNotFoundException;
import com.example.projectcinemaspringboot.model.Room;
import com.example.projectcinemaspringboot.repository.RoomRepository;
import com.example.projectcinemaspringboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class RoomService implements ServiceLayer<Room> {

    RoomRepository roomRepository;

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("Room with id=%d not found", id)));
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room update(Long id, Room room) {
        Room oldRoom = findById(id);
        oldRoom.setName(room.getName());
        oldRoom.setRating(room.getRating());
        oldRoom.setImage(room.getImage());
        roomRepository.save(oldRoom);
        return  oldRoom;
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room findByName(String name) {
        return roomRepository.findByName(name);
    }

    public List<Room> findAllId(Long id) {
        return roomRepository.findAllById(id);
    }
}
