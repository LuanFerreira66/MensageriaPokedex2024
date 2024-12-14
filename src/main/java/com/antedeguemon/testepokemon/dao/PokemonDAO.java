package com.antedeguemon.testepokemon.dao;

import com.antedeguemon.testepokemon.config.ConnectionFactorySQL;
import com.antedeguemon.testepokemon.model.Pokemon;
import com.antedeguemon.testepokemon.model.Treinador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// vai mexer na pokedex do treinador ent lembrar de referenciar a pokedex do treinador
public class PokemonDAO implements iPokemonDao{

    //registrar pokemon em uma pokedex e deve atualizar ao msm tempo qtd capturados e visto
    @Override
    public void registrarPokemon(Pokemon pokemon, Treinador treinador, String status) {

        String query = "INSERT INTO Pokemon(idpokemon, nome, tipo, tiposecundario, habitat, descricao) " +
                        "VALUES (?,?,?,?,?,?)";

        String query1 = "UPDATE PokedexDoTreinador SET TotalVisto = TotalVisto + 1 WHERE IdPokedex = ?";

        String query2 = "UPDATE PokedexDoTreinador SET TotalCapturado = TotalCapturado + 1 WHERE IdPokedex = ?";

        String query3 = "INSERT INTO RegistrosPokedex(IdPokedex,IdPokemon,Status) VALUES (?,?,?)";


        try(Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(query1);
            PreparedStatement ps2 = con.prepareStatement(query2);
            PreparedStatement ps3 = con.prepareStatement(query3);

            //COLOCAR UM IF AND ELSE PRA ALTERAR ENTRA CAPTURADO OU VISTO

            ps.setInt(1,pokemon.getId());
            ps.setString(2, pokemon.getNome());
            ps.setString(3, pokemon.getTipo());
            ps.setString(4, pokemon.getTipoSecundario());
            ps.setString(5, pokemon.getHabitat());
            ps.setString(6, pokemon.getDesc());

            ps1.setInt(1,treinador.getId());

            ps2.setInt(1,treinador.getId());

            ps3.setInt(1,treinador.getId());
            ps3.setInt(2,pokemon.getId());
            ps3.setString(3,status);

            ps.executeUpdate();
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();

            System.out.println("Pokemon adicionado ao catalogo da pokedex");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    // precisa do Id do treinado ou de sua pokedex
    @Override
    public void alterarStatusPokemon(int idPokedex,int idPokemon) {

        String query = "UPDATE registrospokedex SET Status = 'capturado' WHERE IdPokedex = ? AND IdPokemon = ?";

        try(Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,idPokedex);
            ps.setInt(2,idPokemon);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<Pokemon> listarPokemonsRegistrados(int id) {
        String query = "SELECT * FROM registrospokedex WHERE IdPokedex = ?";
        String query1 = "SELECT * FROM pokemon WHERE IdPokemon = ?";
        ArrayList<Pokemon> ListaPokemon  = new ArrayList<>();
        Pokemon pokemon = new Pokemon();


        try(Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(query1);

            ps.setInt(1,id);


            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idPokemon = rs.getInt("IdPokemon");

                ps1.setInt(1,idPokemon);
                ResultSet rs1 = ps1.executeQuery();

                pokemon = new Pokemon();
                if(rs1.next()){
                    pokemon.setId(rs1.getInt("Idpokemon"));
                    pokemon.setNome(rs1.getString("Nome"));
                    pokemon.setTipo(rs1.getString("Tipo"));
                    pokemon.setTipoSecundario(rs1.getString("TipoSecundario"));
                    pokemon.setHabitat(rs1.getString("Habitat"));
                    pokemon.setDesc(rs1.getString("Descricao"));
                }


                ListaPokemon.add(pokemon);


            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ListaPokemon;
    }

    // não precisa
    @Override
    public Pokemon procurarPokemon(int id) {
        Pokemon pokemon = new Pokemon();
        String query = "SELECT * FROM pokemon WHERE IdPokemon = ?";
        try(Connection con = ConnectionFactorySQL.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                pokemon.setId(rs.getInt("IdPokemon"));
                pokemon.setNome(rs.getString("Nome"));
                pokemon.setTipo(rs.getString("Tipo"));
                pokemon.setTipoSecundario(rs.getString("TipoSecundario"));
                pokemon.setHabitat(rs.getString("Habitat"));
                pokemon.setDesc(rs.getString("Descricao"));
            }else{
                System.out.println("Registro não encontrado");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return pokemon;
    }

    //ADICIONAR O METODO PRA PEGAR OS POKEMON DA POKEDEX COMPLETA OFICIAL PRA CAÇAR POKEMON
}
