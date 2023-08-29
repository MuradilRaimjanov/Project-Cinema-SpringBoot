package com.example.projectcinemaspringboot.controller;

import com.example.projectcinemaspringboot.model.Cinema;
import com.example.projectcinemaspringboot.model.Movie;
import com.example.projectcinemaspringboot.service.impl.CinemaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@RequestMapping("/cinema")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CinemaController {

    CinemaService cinemaService;


    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("cinema", new Cinema());
        return "/cin/cinema_login";
    }

    @PostMapping("/save_cinema")
    public String saveCinema(@ModelAttribute Cinema cinema, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        cinema.setImage(multipartFile.getBytes());
        cinemaService.save(cinema);
        return "redirect:/cinema/find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_cinemas", cinemaService.findAll());
        return "/cin/all_cinema";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        cinemaService.delete(id);
        return "redirect:/cinema/find_all";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cinema", cinemaService.findById(id));
        return "/cin/update_cinema";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Cinema cinema, @PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        cinema.setImage(multipartFile.getBytes());
        cinemaService.update(id, cinema);
        return "redirect:/cinema/find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping ("/find_by_name")
    public String findByName(Model model, @RequestParam(value = "text") String name){
        Cinema cinema = cinemaService.findByName(name);
        model.addAttribute("cinema", cinema);
        return "/cin/one_cinema";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Cinema cinema = cinemaService.findById(id);
        if (cinema != null && cinema.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(cinema.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/one/{id}")
    public String oneMovie(Model model, @PathVariable Long id){
        Cinema cinema = cinemaService.findById(id);
        model.addAttribute("cinema", cinema);
        return "/cin/one_cinema";
    }
}
