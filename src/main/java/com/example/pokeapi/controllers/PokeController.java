package com.example.pokeapi.controllers;

import com.example.pokeapi.enteties.Pokemon;
import com.example.pokeapi.services.PokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokeController {
    @Autowired
    private PokeService pokeService;

    @GetMapping
    public ResponseEntity<List<Pokemon>> findpokemon(@RequestParam String name) {
        var pokemon = pokeService.findAll(name);
        return ResponseEntity.ok(pokemon);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> findPokemonById(@PathVariable String id) {
        return ResponseEntity.ok(pokeService.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        var savedPokemon = pokeService.save(pokemon);
        var uri = URI.create("/api/v1/pokemon" + savedPokemon.getId());
        return ResponseEntity.created(uri).body(savedPokemon);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePokemon(@PathVariable String id, @RequestBody Pokemon pokemon){
        pokeService.update(id, pokemon);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePokemon(@PathVariable String id) {
        pokeService.delete(id);
    }
}


