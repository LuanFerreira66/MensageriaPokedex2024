package com.antedeguemon.testepokemon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class Pokemon {
    String nome,tipo,tipoSecundario,habitat,desc;
    int id;


    public Pokemon(String nome, int id, String desc, String habitat, String tipoSecundario, String tipo) {
        this.nome = nome;
        this.id = id;
        this.desc = desc;
        this.habitat = habitat;
        this.tipoSecundario = tipoSecundario;
        this.tipo = tipo;
    }

    public Pokemon() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTipoSecundario() {
        return tipoSecundario;
    }

    public void setTipoSecundario(String tipoSecundario) {
        this.tipoSecundario = tipoSecundario;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }
}
