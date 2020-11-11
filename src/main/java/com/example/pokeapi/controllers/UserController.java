package com.example.pokeapi.controllers;

import com.example.pokeapi.entities.User;
import com.example.pokeapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Find a user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found by name")
    })
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<User>> getUser(@RequestParam(required = false) String name) {
        var user = userService.findAll(name);
        return ResponseEntity.ok(user);

    }

    @Operation(summary = "Find a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found by id", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found by id", content = @Content)
    })
    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A new user has been created"),
            @ApiResponse(responseCode = "400", description = "Requires a JSON request"),
            @ApiResponse(responseCode = "400", description = "User needs a password"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Username should be between 4 and 15 characters"),
            @ApiResponse(responseCode = "404", description = "Password should contain at least 6 characters")
    })
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> saveUser(@Validated @RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @Operation(summary = "Update an user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The user has been updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden! Cannot update other's information (Admin bypasses this)"),
            @ApiResponse(responseCode = "404", description = "User can not be found by id"),

    })
    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String id, @Validated @RequestBody User user) {
        userService.update(id, user);
    }

    @Operation(summary = "Delete an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User has been deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}

