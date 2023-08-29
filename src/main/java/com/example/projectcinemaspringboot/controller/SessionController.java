package com.example.projectcinemaspringboot.controller;

import com.example.projectcinemaspringboot.model.Cinema;
import com.example.projectcinemaspringboot.model.Movie;
import com.example.projectcinemaspringboot.model.Room;
import com.example.projectcinemaspringboot.model.Session;
import com.example.projectcinemaspringboot.service.impl.MovieService;
import com.example.projectcinemaspringboot.service.impl.RoomService;
import com.example.projectcinemaspringboot.service.impl.SessionService;
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
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/session")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SessionController {

    SessionService sessionService;

    MovieService movieService;

    RoomService roomService;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ModelAttribute("movieList")
    public List<Movie> movieList() {
        return movieService.findAll();
    }



    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ModelAttribute("roomList")
    public List<Room> roomList() {
        return roomService.findAll();
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("ses", new Session());
        return "/sess/session_login";
    }

    @PostMapping("/save_session")
    public String saveCinema(@ModelAttribute Session session, @RequestParam("file")MultipartFile multipartFile) throws IOException {
        session.setFinish(LocalDateTime.now().plusHours(session.getDuration()));
        session.setImage(multipartFile.getBytes());
        sessionService.save(session);
        return "redirect:/session/find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_session", sessionService.findAll());
        return "/sess/all_session";
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/findAllId/{id}")
    public String findAllId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("all_session_id", sessionService.findAllId(id));
        return "/sess/all_session_id";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ses", sessionService.findById(id));
        return "sess/one_session";
    }
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        sessionService.delete(id);
        return "redirect:/session/find_all";
    }

    @GetMapping("/update/{movie_id}")
    public String update(@PathVariable("session_id") Long id, Model model) {
        model.addAttribute("ses", sessionService.findById(id));
        return "/sess/update_session";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Session session, @PathVariable Long id){
        sessionService.update(id, session);
        return "redirect:/sess/find_all";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping ("/find_by_name")
    public String findByName(Model model, @RequestParam(value = "text") String name){
        Session session = sessionService.findByName(name);
        model.addAttribute("sess", session);
        return "/sess/one_session";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Session session = sessionService.findById(id);
        if (session != null && session.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(session.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
