package com.example.projectcinemaspringboot.service.impl;

import com.example.projectcinemaspringboot.exception.EntityNotFoundException;
import com.example.projectcinemaspringboot.model.Movie;
import com.example.projectcinemaspringboot.repository.MovieRepository;
import com.example.projectcinemaspringboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MovieService implements ServiceLayer<Movie> {

    MovieRepository movieRepository;

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("Movie with id=%d not found", id)));
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie update(Long id, Movie movie) {
        Movie oldMovie = findById(id);
        oldMovie.setName(movie.getName());
        oldMovie.setCountry(movie.getCountry());
        oldMovie.setCreated(movie.getCreated());
        oldMovie.setLanguage(movie.getLanguage());
        oldMovie.setGenre(movie.getGenre());
        oldMovie.setImage(movie.getImage());
        movieRepository.save(oldMovie);
        return oldMovie;
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Movie findByName(String name) {
        return movieRepository.findByName(name);
    }
}
