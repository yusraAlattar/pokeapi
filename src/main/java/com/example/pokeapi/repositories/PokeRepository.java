package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokeRepository extends MongoRepository<Pokemon, String> {
}
