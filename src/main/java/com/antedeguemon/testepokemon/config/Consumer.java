package com.antedeguemon.testepokemon.config;


import com.antedeguemon.testepokemon.dao.PokemonDAO;
import com.antedeguemon.testepokemon.dao.TreinadorDAO;
import com.antedeguemon.testepokemon.model.Pokemon;
import com.antedeguemon.testepokemon.model.Treinador;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class Consumer {
    private static final String QUEUE_NAME = Config.getProperty("amqp.queueName");
    private static final String URI = Config.getProperty("amqp.uri");

    //ta funcionando
    public static void consumirRegistroTreinador() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()){


            channel.queueDeclare("fila-Treinador",true, false,false,null);
            System.out.println("Esperando mensagem");
            DeliverCallback deliverCallback = (consumerTag, delivery) ->{
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.err.println("Registro de treinador recebido: " + message);


                //PROCESSAR MENSAGEM AQUI !!!!! coisas do treinador

                Treinador treinador = new Treinador(message);
                System.out.println("NOME SALVO"+ treinador.getNome());

                try {
                    // 2. Salvar no banco de dados
                    TreinadorDAO treinadorDAO = new TreinadorDAO();
                    treinadorDAO.registrarTreinador(treinador);
                }catch (Exception e){
                    System.err.println("Erro ao processar mensagem: " + e.getMessage());
                    e.printStackTrace();
                }

            };

            channel.basicConsume("fila-Treinador",true,deliverCallback,consumerTag -> {});


        } catch (IOException | TimeoutException e){
            throw new RuntimeException(e);
        }

    }


    public static void consumirRegistroPokemon() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()){
            channel.queueDeclare("fila-Pokemon",true, false,false,null);
            System.out.println("Esperando mensagem");
            DeliverCallback deliverCallback = (consumerTag, delivery) ->{
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.err.println("Registro de pokemon recebido: " + message);

                String[] partes = message.split(" ");

                //precisa do Id do treinador tb
                int idPokemon = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String tipo = partes[2];
                String tipoSecundario = partes[3];
                String habitat = partes[4];
                String desc = partes[5];
                int idTreinador = Integer.parseInt(partes[6]);

                Pokemon pokemon = new Pokemon(nome,idPokemon,desc,habitat,tipoSecundario,tipo);

                PokemonDAO pokemonDAO = new PokemonDAO();


                Treinador treinador = new Treinador(" ");
                treinador.setId(idTreinador);

                //precisa do treinador e do status de visto or capturado
                pokemonDAO.registrarPokemon(pokemon,treinador,"Visto");


            };

            channel.basicConsume("fila-Pokemon",true,deliverCallback,consumerTag -> {});


        } catch (IOException | TimeoutException e){
            throw new RuntimeException(e);
        }

    }


    // funcionando
    public static void consumirAlterarPokemon() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()){
            channel.queueDeclare("fila-AlterarPokemon",true, false,false,null);
            System.out.println("Esperando mensagem");
            DeliverCallback deliverCallback = (consumerTag, delivery) ->{
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.err.println("Registro de pokemon recebido: " + message);

                String[] partes = message.split(" ");

                int idPokedex = Integer.parseInt(partes[0]);
                int idPokemon = Integer.parseInt(partes[1]);

                PokemonDAO pokemonDAO = new PokemonDAO();
                pokemonDAO.alterarStatusPokemon(idPokedex,idPokemon);
            };

            channel.basicConsume("fila-AlterarPokemon",true,deliverCallback,consumerTag -> {});


        } catch (IOException | TimeoutException e){
            throw new RuntimeException(e);
        }

    }

    // ALTERAR PRA RECEBER UM ARRAY DE POKEMONS DA POKEDEX

    public ArrayList<Pokemon> consumirDadosPokedex() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);
        ArrayList<Pokemon> ListaDePokemons = new ArrayList<>();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()){

            channel.queueDeclare("fila-ReceberDadosPokedex",true, false,false,null);
            System.out.println("Esperando mensagem");
            DeliverCallback deliverCallback = (consumerTag, delivery) ->{
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.err.println("Registro de pokemon recebido: " + message);

                int idTreinador = Integer.parseInt(message);

                PokemonDAO pokemonDAO = new PokemonDAO();

                ListaDePokemons.addAll(pokemonDAO.listarPokemonsRegistrados(idTreinador));


                //PROCESSAR MENSAGEM AQUI !!!!! receber dados pokedex
            };

            channel.basicConsume("fila-ReceberDadosPokedex",true,deliverCallback,consumerTag -> {});

        } catch (IOException | TimeoutException e){
            throw new RuntimeException(e);
        }

        return ListaDePokemons;

    }




}
