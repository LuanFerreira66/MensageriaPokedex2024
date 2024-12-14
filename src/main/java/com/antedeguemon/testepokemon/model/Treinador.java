package com.antedeguemon.testepokemon.model;


public class Treinador {
    private String nome;
    int id;

    public Treinador(String nome){
        this.nome = nome;
    }

    public Treinador(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
