package com.antedeguemon.testepokemon.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


public class Producer {
    //seta nome da fila , URI seria o host
    private static final String QUEUE_NAME = Config.getProperty("amqp.queueName");
    private static final String URI = Config.getProperty("amqp.uri");



    //funcionando
    public static void runProducerTreinador(String mensagem) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        Scanner sc = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);

        //vai passar pela fila do RabbitMQ primeiro e depois faz o registro no banco de dados
        try(Connection connection = factory.newConnection();Channel channel = connection.createChannel()){
            // cria ou conecta em uma fila... seu cachorro e fila ?
            channel.queueDeclare("fila-Treinador",true,false,false,null);
            System.out.println("Aguardando mensagem. ");

            channel.basicPublish("","fila-Treinador",null,mensagem.getBytes());
            System.err.println("Eviando Treinador: '"+mensagem+"'");

            Thread.sleep(100);

        } catch (IOException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }



    // ta funcionando
    public static void runProducerPokemon(String idPokemon,String nome, String tipo, String tipoSecundario
    , String habitat, String desc, String idTreinador) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        Scanner sc = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);

        //vai passar pela fila do RabbitMQ primeiro e depois faz o registro no banco de dados
        try(Connection connection = factory.newConnection();Channel channel = connection.createChannel()){

            channel.queueDeclare("fila-Pokemon",true,false,false,null);
            System.out.println("Aguardando mensagem. ");


            // arruma a mensagem para mandar pra fila
            String mensagem = idPokemon+" "+nome+" "+tipo+" "+tipoSecundario+" "+habitat+" "+desc+" "+idTreinador;


            channel.basicPublish("","fila-Pokemon",null,mensagem.getBytes());
            System.err.println("Eviando dados do Pokemon: '"+mensagem+"'");


            Thread.sleep(100);



        } catch (IOException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    //ALTERAR STATUS VISTO PRA CAPTURADO
    public static void runProducerAlterarPokemon() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        Scanner sc = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);

        //vai passar pela fila do RabbitMQ primeiro e depois faz o registro no banco de dados
        try(Connection connection = factory.newConnection();Channel channel = connection.createChannel()){
            // cria ou conecta em uma fila... seu cachorro e fila ?
            channel.queueDeclare("fila-AlterarPokemon",true,false,false,null);
            System.out.println("Aguardando mensagem. ");


            String idpokedex = sc.next();
            String idpokemon = sc.next();
            String mensagem = idpokedex + " " + idpokemon;


            channel.basicPublish("","fila-AlterarPokemon",null,mensagem.getBytes());
            System.err.println("Eviando Alteração de Pokemon: '"+mensagem+"'");


            Thread.sleep(100);



        } catch (IOException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    // pra puxar os pokemon da pokedex do cara
    public static void runProducerReceberPokemons(String id) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        Scanner sc = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);

        //vai passar pela fila do RabbitMQ primeiro e depois faz o registro no banco de dados
        try(Connection connection = factory.newConnection();Channel channel = connection.createChannel()){
            // cria ou conecta em uma fila... seu cachorro e fila ?
            channel.queueDeclare("fila-ReceberDadosPokedex",true,false,false,null);
            System.out.println("Aguardando mensagem. ");

            // consulta pelo id do treinador
            channel.basicPublish("","fila-ReceberDadosPokedex",null,id.getBytes());
            System.err.println("Solicitados dados da pokedex: '"+id+"'");


            Thread.sleep(100);



        } catch (IOException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
