package com.example.projectcinemaspringboot.controller;

import com.example.projectcinemaspringboot.model.Cinema;
import com.example.projectcinemaspringboot.model.Movie;
import com.example.projectcinemaspringboot.service.impl.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MovieController {

    MovieService movieService;

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("movie", new Movie());
        return "/mov/movie_login";
    }

    @PostMapping("/save_movie")
    public String saveCinema(@ModelAttribute Movie movie, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        movie.setImage(multipartFile.getBytes());
        movieService.save(movie);
        return "redirect:/movie/find_all";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_movie", movieService.findAll());
        return "/mov/all_movie";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        return "/mov/one_movie";
    }
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        movieService.delete(id);
        return "redirect:/movie/find_all";
    }

    @GetMapping("/update/{movie_id}")
    public String update(@PathVariable("movie_id") Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        return "/mov/update_movie";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Movie movie, @PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        movie.setImage(multipartFile.getBytes());
        movieService.update(id, movie);
        return "redirect:/movie/find_all";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping ("/find_by_name")
    public String findByName(Model model, @RequestParam(value = "text") String name) {
        Movie movie = movieService.findByName(name);
        model.addAttribute("movie", movie);
        return "/mov/one_movie";
    }

    @GetMapping("/one/{id}")
    public String oneMovie(Model model, @PathVariable Long id){
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "/mov/one_movie";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Movie movie = movieService.findById(id);
        if (movie != null && movie.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(movie.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
