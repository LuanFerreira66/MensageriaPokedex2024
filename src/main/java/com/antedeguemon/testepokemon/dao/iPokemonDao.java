package com.antedeguemon.testepokemon.dao;

import com.antedeguemon.testepokemon.model.Pokemon;
import com.antedeguemon.testepokemon.model.Treinador;

import java.util.List;

public interface iPokemonDao {
    void registrarPokemon(Pokemon pokemon, Treinador treinador, String status);
    void alterarStatusPokemon(int idPokedex,int idPokemon);
    List<Pokemon> listarPokemonsRegistrados(int id);
    Pokemon procurarPokemon(int id);
}
