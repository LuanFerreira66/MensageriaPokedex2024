package com.antedeguemon.testepokemon;

import com.antedeguemon.testepokemon.config.Consumer;
import com.antedeguemon.testepokemon.config.Producer;
import com.antedeguemon.testepokemon.dao.PokedexDAO;
import com.antedeguemon.testepokemon.dao.PokemonDAO;
import com.antedeguemon.testepokemon.dao.TreinadorDAO;
import com.antedeguemon.testepokemon.model.PokedexDoTreinador;
import com.antedeguemon.testepokemon.model.Pokemon;
import com.antedeguemon.testepokemon.model.Treinador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    Producer produtor = new Producer();
    Consumer consumidor = new Consumer();
    PokedexDoTreinador pokedexDoTreinador= new PokedexDoTreinador();
    Treinador realTrainer = new Treinador();


    @FXML
    private Text InfoNomeTreinador;

    @FXML
    private Text InforIdTreinador;

    @FXML
    private Button ListarPokemons;

    @FXML
    private VBox Options;

    @FXML
    private Text TotalVisto;

    @FXML
    private Text TotalVisto1;

    @FXML
    private Button atualizarStatus;

    @FXML
    private Button cadastroPokemon;

    @FXML
    private Label descricao;

    @FXML
    private Label descricao1;

    @FXML
    private Label habitat;

    @FXML
    private Label nomePokemon;

    @FXML
    private Button opcoesTreinador;

    @FXML
    private ScrollPane scrollPanePokemon;

    @FXML
    private TextFlow textoDosPokemons;

    @FXML
    private Label tipoPokemon;

    @FXML
    private Label tipoSecundario;

    @FXML
    private Button trocarTreinador;

    @FXML
    private TextField trocarTreinadorField;

    @FXML
    private TextField infoTxt;

    @FXML
    private Button verificarInfo;

    @FXML
    void btnAtualizarStatsPokemon(ActionEvent event) {
        PokedexDAO pokedexDAO = new PokedexDAO();
        pokedexDAO.atualizarStatus(Integer.parseInt(infoTxt.getText()), realTrainer.getId());
    }

    @FXML
    void btnCadastrarPokemon(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pokemon.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("CadastroTreinador");
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCadastroTreinador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("options-treinador.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("CadastroTreinador");
            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    @FXML
    void btnListarPokemons(ActionEvent event) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        String id = ""+realTrainer.getId();
        Producer.runProducerReceberPokemons(id);

        List<Pokemon> listaPokemon = consumidor.consumirDadosPokedex();
        listaPokemon.addAll(consumidor.consumirDadosPokedex());

        textoDosPokemons.getChildren().clear();

        for(int i=0;i<listaPokemon.size();i++){
            String string = listaPokemon.get(i).toString();

            Text text = new Text(string + "\n");

            textoDosPokemons.getChildren().add(text);
        }

    }

    @FXML
    void btnTrocarTreinador(ActionEvent event) {
        String nome = trocarTreinadorField.getText();
        TreinadorDAO treinador = new TreinadorDAO();
        PokedexDAO pokedexDAO = new PokedexDAO();

        realTrainer = treinador.getTreinador(nome);

        InfoNomeTreinador.setText("Nome Treinador: " + nome);
        InforIdTreinador.setText("Id Treinador: " + realTrainer.getId());

        pokedexDoTreinador = pokedexDAO.registroPokedex(realTrainer.getId());

        TotalVisto.setText("Total Visto: " + pokedexDoTreinador.getTotalVisto());
        TotalVisto1.setText("Total Capturado: " + pokedexDoTreinador.getTotalCapturado());

    }

    @FXML
    void btnVerificarInfo(ActionEvent event) {
        PokemonDAO pokemonDAO = new PokemonDAO();
        PokedexDAO pokedexDAO = new PokedexDAO();
        Pokemon pokemon =  pokemonDAO.procurarPokemon(Integer.parseInt(infoTxt.getText()));

        nomePokemon.setText(pokemon.getNome());
        tipoPokemon.setText(pokemon.getTipo());
        tipoSecundario.setText(pokemon.getTipoSecundario());
        habitat.setText(pokemon.getHabitat());
        descricao.setText(pokemon.getDesc());

        descricao1.setText(pokedexDAO.verificarStatusPokemon(pokemon.getId(),realTrainer.getId()));


    }

}
