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

public class OptionsController {

    @FXML
    private Button cadastrarTreinador;

    @FXML
    private TextField nomeTreinadorCadastro;

    @FXML
    void btnCadastro(ActionEvent event) {
        String nome = nomeTreinadorCadastro.getText();
        try {
            Producer.runProducerTreinador(nome);
            Consumer.consumirRegistroTreinador();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
