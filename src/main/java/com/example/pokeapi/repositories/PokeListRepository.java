package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.PokemonList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface PokeListRepository extends MongoRepository<PokemonList, String> {


    ArrayList<String> findByNames(String names);
}
