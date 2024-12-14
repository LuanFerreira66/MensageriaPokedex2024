package com.antedeguemon.testepokemon.dao;

import com.antedeguemon.testepokemon.model.PokedexDoTreinador;
import com.antedeguemon.testepokemon.model.Pokemon;

public interface iPokedexDao {
    public PokedexDoTreinador registroPokedex(int id);
    public String verificarStatusPokemon(int idPokemon,int IdTreinador);
    public void atualizarStatus(int idPokemon,int idPokedex);
}
