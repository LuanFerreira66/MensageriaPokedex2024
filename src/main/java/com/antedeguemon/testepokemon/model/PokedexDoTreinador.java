package com.antedeguemon.testepokemon.model;

public class PokedexDoTreinador {
    private int idPokedex , totalVisto, totalCapturado;
    private RegistrosPokedex registrosPokedex;

    public PokedexDoTreinador(int totalCapturado, int totalVisto, int idPokedex) {
        this.totalCapturado = totalCapturado;
        this.totalVisto = totalVisto;
        this.idPokedex = idPokedex;
    }

    public PokedexDoTreinador(){}

    public int getIdPokedex() {
        return idPokedex;
    }

    public void setIdPokedex(int idPokedex) {
        this.idPokedex = idPokedex;
    }

    public int getTotalVisto() {
        return totalVisto;
    }

    public void setTotalVisto(int totalVisto) {
        this.totalVisto = totalVisto;
    }

    public int getTotalCapturado() {
        return totalCapturado;
    }

    public void setTotalCapturado(int totalCapturado) {
        this.totalCapturado = totalCapturado;
    }

    public RegistrosPokedex getRegistrosPokedex() {
        return registrosPokedex;
    }

    public void setRegistrosPokedex(RegistrosPokedex registrosPokedex) {
        this.registrosPokedex = registrosPokedex;
    }
}
