package com.mycompany.prj_nota.Pck_Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql {
    private Connection conn;
    private String url;
    private String user;
    private String password;
    private String driver;

    public ConexaoMySql() {
        Config config = new Config("config.properties");
        url = config.get("db.url");
        user = config.get("db.user");
        password = config.get("db.password");
        driver = config.get("db.driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.toString());
        }
    }

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Conectado ao banco");
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.toString());
        }
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão encerrada");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.toString());
        }
    }
}
