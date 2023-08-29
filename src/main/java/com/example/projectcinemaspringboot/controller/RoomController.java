package com.example.projectcinemaspringboot.controller;

import com.example.projectcinemaspringboot.model.Cinema;
import com.example.projectcinemaspringboot.model.Movie;
import com.example.projectcinemaspringboot.model.Room;
import com.example.projectcinemaspringboot.service.impl.CinemaService;
import com.example.projectcinemaspringboot.service.impl.RoomService;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoomController {

    RoomService roomService;
    CinemaService cinemaService;


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ModelAttribute("cinemaList")
    public List<Cinema> cinemaList() {
        return cinemaService.findAll();
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("room", new Room());
        return "/room/room_login";
    }

    @PostMapping("/save_room")
    public String saveCinema(@ModelAttribute Room room, @RequestParam("file")MultipartFile multipartFile) throws IOException {
        room.setImage(multipartFile.getBytes());
        roomService.save(room);
        return "redirect:/room/find_all";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_room", roomService.findAll());
        return "/room/all_room";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/findAllId/{id}")
    public String findAllId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("all_room_id", roomService.findAllId(id));
        return "/room/all_room_id";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        return "/room/one_room";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        roomService.delete(id);
        return "redirect:/room/find_all";
    }

    @GetMapping("/update/{room_id}")
    public String update(@PathVariable("room_id") Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        return "/room/update_room";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Room room, @PathVariable Long id){
        roomService.update(id, room);
        return "redirect:/room/find_all";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Room room = roomService.findById(id);
        if (room != null && room.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(room.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping ("/find_by_name")
    public String findByName(Model model, @RequestParam(value = "text") String name) {
        Room room = roomService.findByName(name);
        model.addAttribute("room", room);
        return "/room/one_room";
    }

    @GetMapping("/one/{id}")
    public String oneMovie(Model model, @PathVariable Long id){
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "/room/one_room";
    }
}
