package com.example.projectcinemaspringboot.controller;

import com.example.projectcinemaspringboot.model.Movie;
import com.example.projectcinemaspringboot.model.Place;
import com.example.projectcinemaspringboot.model.Room;
import com.example.projectcinemaspringboot.service.impl.PlaceService;
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
@RequestMapping("/place")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PlaceController {

    PlaceService placeService;

    RoomService roomService;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ModelAttribute("roomList")
    public List<Room> roomList() {
        return roomService.findAll();
    }


    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("place", new Place());
        return "/plac/place_login";
    }

    @PostMapping("/save_place")
    public String saveCinema(@ModelAttribute Place place, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        place.setImage(multipartFile.getBytes());
        placeService.save(place);
        return "redirect:/place/find_all";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_place", placeService.findAll());
        return "/plac/all_place";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("place", placeService.findById(id));
        return "/plac/one_place";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        placeService.delete(id);
        return "redirect:/place/find_all";
    }

    @GetMapping("/update/{place_id}")
    public String update(@PathVariable("place_id") Long id, Model model) {
        model.addAttribute("place", placeService.findById(id));
        return "/plac/update_place";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Place place, @PathVariable Long id){
        placeService.update(id, place);
        return "redirect:/place/find_all";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/buy")
    public String buy(){
        return "/plac/buy";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/findAllId/{id}")
    public String findAllId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("all_places", placeService.findAllById(id));
        return "/plac/all_place_id";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Place place = placeService.findById(id);
        if (place != null && place.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(place.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
