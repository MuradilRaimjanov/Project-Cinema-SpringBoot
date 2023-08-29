package com.example.projectcinemaspringboot.service.impl;

import com.example.projectcinemaspringboot.exception.EntityNotFoundException;
import com.example.projectcinemaspringboot.model.Place;
import com.example.projectcinemaspringboot.repository.PlaceRepository;
import com.example.projectcinemaspringboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PlaceService implements ServiceLayer<Place> {

    PlaceRepository placeRepository;

    @Override
    public void save(Place place) {
        placeRepository.save(place);
    }

    @Override
    public Place findById(Long id) {
        return placeRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("Place with id=%d not found", id)));
    }

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public Place update(Long id, Place place) {
        Place oldPlace = findById(id);
        oldPlace.setY(place.getY());
        oldPlace.setX(place.getX());
        oldPlace.setPrice(oldPlace.getPrice());
        placeRepository.save(oldPlace);
        return oldPlace;
    }

    @Override
    public void delete(Long id) {
        placeRepository.deleteById(id);
    }

    @Override
    public Place findByName(String name) {
        return null;
    }

    public List<Place> findAllById(Long id) {
        return placeRepository.findAllById(id);
    }
}
