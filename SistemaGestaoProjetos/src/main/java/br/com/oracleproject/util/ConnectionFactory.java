package br.com.oracleproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/sistema";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8000000p";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {

            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}