package com.example.pokeapi.controllers;

import com.example.pokeapi.entities.User;
import com.example.pokeapi.services.UserService;
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

    @GetMapping
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<List<User>> getUser(@RequestParam(required = false) String name) {
        var user = userService.findAll(name);
        return ResponseEntity.ok(user);

    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> saveUser(@Validated @RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String id, @Validated @RequestBody User user) {
        userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}

