package com.antedeguemon.testepokemon;

import com.antedeguemon.testepokemon.config.Consumer;
import com.antedeguemon.testepokemon.config.Producer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class PokemonController {

    @FXML
    private TextField desc;

    @FXML
    private TextField habitat;

    @FXML
    private TextField idPokedex;

    @FXML
    private TextField idPokemon;

    @FXML
    private TextField nome;

    @FXML
    private Button sair;

    @FXML
    private Button salvarPokemon;

    @FXML
    private TextField tipo;

    @FXML
    private TextField tipoSecundario;

    @FXML
    void btnSair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSalvarPokemon(ActionEvent event) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException {

        Producer.runProducerPokemon(idPokemon.getText(),nome.getText(),tipo.getText(),tipoSecundario.getText(),
                habitat.getText(),desc.getText(),idPokedex.getText());
        Thread.sleep(5000);
        Consumer.consumirRegistroPokemon();

    }

}

