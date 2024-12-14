package com.antedeguemon.testepokemon.dao;

import com.antedeguemon.testepokemon.config.ConnectionFactorySQL;
import com.antedeguemon.testepokemon.model.Treinador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TreinadorDAO implements iTreinadorDao{
    @Override
    public void registrarTreinador(Treinador treinador) {
        String query = "INSERT INTO Treinador(nome) VALUES (?)";
        Treinador treinador1 = treinador;

        try (Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1,treinador.getNome());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            //apos criar a table treinado a pokedex ja deve ser gerada para este Treinador
            if(rs.next()){
                treinador.setId(rs.getInt(1));
                criarPokedex(treinador);
            }

            System.out.println("Id de Treinador: " + treinador.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Buscar todos treinadores registrados
    @Override
    public List<Treinador> listarTreinadores() {
        List<Treinador> listaTreinadores = new ArrayList<>();
        Treinador treinador = new Treinador();
        String query = "SELECT * FROM treinador";

        try (Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                treinador = new Treinador();
                treinador.setId(rs.getInt("IdTreinador"));
                treinador.setNome(rs.getString("nome"));
                listaTreinadores.add(treinador);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listaTreinadores;
    }

    //pegar dados de um unico treinador, verificar se busca por nome ou Id qual seria better
    public Treinador getTreinador(String nome){
        Treinador treinador = new Treinador();
        String query = "SELECT * FROM treinador WHERE Nome = ?";

        try (Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,nome);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("Treinador encontrado");
                treinador.setId(rs.getInt("IdTreinador"));
                treinador.setNome(rs.getString("Nome"));
            }else{
                System.out.println("Não encontrado");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return treinador;
    }

    //cria uma pokedex para o treinado na mesma hora que acontece o registro do novo treinador
    private void criarPokedex(Treinador treinador){
        String query = "INSERT INTO PokedexDoTreinador(IdPokedex,TotalVisto,TotalCapturado) VALUES (?,?,?)";

        try (Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1,treinador.getId());
            ps.setInt(2,0);
            ps.setInt(3,0);
            ps.executeUpdate();

            System.out.println("Criado pokedex deste treinador");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
