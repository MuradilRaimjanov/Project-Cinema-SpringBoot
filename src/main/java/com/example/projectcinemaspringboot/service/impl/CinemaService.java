package com.example.projectcinemaspringboot.service.impl;

import com.example.projectcinemaspringboot.exception.EntityNotFoundException;
import com.example.projectcinemaspringboot.model.Cinema;
import com.example.projectcinemaspringboot.repository.CinemaRepository;
import com.example.projectcinemaspringboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CinemaService implements ServiceLayer<Cinema> {

    CinemaRepository cinemaRepository;
    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public Cinema findById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("Cinema with id=%d not found!!!", id)));
    }

    @Override
    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema update(Long id, Cinema cinema) {
        Cinema oldCinema = findById(id);
        oldCinema.setName(cinema.getName());
        oldCinema.setAddress(cinema.getAddress());
        oldCinema.setImage(cinema.getImage());
        cinemaRepository.save(oldCinema);
        return oldCinema;
    }

    @Override
    public void delete(Long id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    public Cinema findByName(String name) {
        return cinemaRepository.findByName(name);
    }
}
