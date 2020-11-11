package com.example.pokeapi.controllers;

import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.services.PokeConsumerService;
import com.example.pokeapi.services.PokeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokeController {
    @Autowired
    private PokeService pokeService;
    @Autowired
    private PokeConsumerService consumerService;

    @Operation(summary = "By using a query you can find a list of Pokemons. Partial name search is allowed",
            description = "Returns a list of a pokemon and its depending on a query argument, If query is missing a list if all Pokemons sabed in database will be returned. No auth reqyuired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Pokemon"),
            @ApiResponse(responseCode = "400", description = "Name search must contain at least 3 characters", content = @Content),
            @ApiResponse(responseCode = "400", description = "Pokemon list could not be populated (Used in a partial String search", content = @Content),
            @ApiResponse(responseCode = "404", description = "Could not find a Pokemon", content = @Content)

    })
    @GetMapping
    public ResponseEntity<List<Pokemon>> findpokemon(
            @Parameter(description = "The allowed queries are: name, minWeight, minHeight, maxWeight, maxHeight")
            @RequestParam Map<String, String> query) {
        var pokemon = pokeService.findAll(query);
        return ResponseEntity.ok(pokemon);
    }

    @Operation(summary = "Find a Pokemon by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Pokemon")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> findPokemonById(@PathVariable String id) {
        return ResponseEntity.ok(pokeService.findById(id));
    }

    @Operation(summary = "Create a Pokemon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A new Pokemon is now created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})

    public ResponseEntity<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        var savedPokemon = pokeService.save(pokemon);
        var uri = URI.create("/api/v1/pokemon" + savedPokemon.getId());
        return ResponseEntity.created(uri).body(savedPokemon);
    }

    @Operation(summary = "Update a Pokemon")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "204", description = "Pokemon is now updated", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pokemon can not be found by id", content = @Content)
    })
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePokemon(@PathVariable String id, @RequestBody Pokemon pokemon) {
        pokeService.update(id, pokemon);
    }

    @Operation(summary = "Delete a Pokomon by id")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "204", description = "Pokemon is now deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Pokemon can not be found by id")
    })

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePokemon(@PathVariable String id) {
        pokeService.delete(id);
    }

    @Operation(summary = "Delete Pokemons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pokemon has been deleted", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pokemon can not be found by id", content = @Content)
    })
    @DeleteMapping("/all")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteAllPokemon() {
        pokeService.deleteAllEntries();

    }


}
