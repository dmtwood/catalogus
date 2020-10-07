package be.vdab.catalogus.controllers;

import be.vdab.catalogus.domain.Artikel;
import be.vdab.catalogus.services.ArtikelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artikels")
public class ArtikelController {

    private final ArtikelService artikelService;

    public ArtikelController(ArtikelService artikelService) {
        this.artikelService = artikelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    // post contains a requestbody holding the name for a new Artikel
    void post(@RequestBody String naam) {

        // service creates the Artikel in the database & sends message to RabbitMQ
        artikelService.create( new Artikel(naam) );
    }
}
