package com.antedeguemon.testepokemon.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactorySQL {

    private ConnectionFactorySQL(){}

    public static Connection getConnection(){
        try{
            //Editar para conectar com o banco de dados
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/atvpokemon",
                    "root",
                    "Luan@3214");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
