package sg.edu.nus.iss.day36giphy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import sg.edu.nus.iss.day36giphy.service.AppService;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin()
public class AppController {

    @Autowired
    private AppService service;

    @GetMapping(path = "/gifs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGifs(@RequestParam String search) {

        List<String> gifList = service.getGifsFromAPI(search);
        JsonArray gifArray = Json.createArrayBuilder(gifList).build();

        return ResponseEntity.ok(gifArray.toString());
    }
    
}
