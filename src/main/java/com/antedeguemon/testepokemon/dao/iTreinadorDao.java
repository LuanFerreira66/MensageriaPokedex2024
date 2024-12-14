package com.antedeguemon.testepokemon.dao;

import com.antedeguemon.testepokemon.model.Treinador;

import java.util.List;

public interface iTreinadorDao {
    void registrarTreinador(Treinador treinador);
    List<Treinador> listarTreinadores();
}
