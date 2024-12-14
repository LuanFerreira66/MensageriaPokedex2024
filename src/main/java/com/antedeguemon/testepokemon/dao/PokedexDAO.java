package com.antedeguemon.testepokemon.dao;

import com.antedeguemon.testepokemon.config.ConnectionFactorySQL;
import com.antedeguemon.testepokemon.model.PokedexDoTreinador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PokedexDAO implements iPokedexDao{

    @Override
    public PokedexDoTreinador registroPokedex(int id) {
        String quer = "SELECT * FROM pokedexdotreinador WHERE  IdPokedex = ?";
        PokedexDoTreinador pokedexDoTreinador = new PokedexDoTreinador();

        try(Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(quer);
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                pokedexDoTreinador.setIdPokedex(rs.getInt("IdPokedex"));
                pokedexDoTreinador.setTotalCapturado(rs.getInt("TotalCapturado"));
                pokedexDoTreinador.setTotalVisto(rs.getInt("TotalVisto"));
            }else {
                System.out.println("Nenhum registro encontrado");
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pokedexDoTreinador;

    }

    @Override
    public String verificarStatusPokemon(int idPokemon, int IdTreinador) {
        String query = "SELECT * FROM registrospokedex WHERE IdPokedex = ? and IdPokemon = ?";
        String status = " ";

        try(Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,IdTreinador);
            ps.setInt(2,idPokemon);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                status = rs.getString("Status");
            }else {
                System.out.println("Não encontrado");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    @Override
    public void atualizarStatus(int idPokemon, int idPokedex) {
        String query = "UPDATE registrospokedex SET Status = 'Capturado' WHERE IdPokedex = ? AND IdPokemon = ?";

        try(Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,idPokedex);
            ps.setInt(2,idPokemon);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
