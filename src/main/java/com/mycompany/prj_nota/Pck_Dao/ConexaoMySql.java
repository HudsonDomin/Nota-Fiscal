package com.mycompany.prj_nota.Pck_Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql  {
    private final String sDriver = "com.mysql.cj.jdbc.Driver";
    private final String sUrl = "jdbc:mysql://localhost:3308/db_nota?useSSL=false";
    private final String sLogin = "root";
    private final String sSenha = "";
    public Connection conn = null;

    public ConexaoMySql(){}

    public boolean getConnection(){
        try{
            Class.forName(sDriver);
            conn = DriverManager.getConnection(sUrl, sLogin, sSenha);
            System.out.println("Conectou");
            return true;
        }catch (ClassNotFoundException erro){
            System.out.println("Driver nao encontrado" + erro.toString());
            return false;
        }catch (SQLException erro){
            System.out.println("Falha ao conectar" + erro.toString());
            return false;
        }
    }

}
