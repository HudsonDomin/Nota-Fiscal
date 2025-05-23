package com.mycompany.prj_nota.Pck_Persistencia;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ClienteModel;
import com.mycompany.prj_nota.Pck_Control.ClienteControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientePersistencia {
    public void inserirClientePersistencia(ClienteModel oClienteModel){
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsCliente(?, ?, ?, ?, ?)}")) {

            stmt.setString(1, oClienteModel.getA01_nome());
            stmt.setString(2, oClienteModel.getA01_endereco());
            stmt.setString(3, oClienteModel.getA01_telefone());
            stmt.setString(4, oClienteModel.getA01_cpf());
            stmt.setDouble(5, oClienteModel.getA01_credito());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }
    public void removerClientePersistencia(ClienteModel oClienteModel){
    ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_DelCliente(?)}")) {

            stmt.setInt(1, oClienteModel.getA01_codigo());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }
    }
    
    public void atualizarClientePersistencia(ClienteModel oClienteModel){
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdCliente(?, ?, ?, ?, ?, ?)}")) {

            stmt.setInt(1, oClienteModel.getA01_codigo());
            stmt.setString(2, oClienteModel.getA01_nome());
            stmt.setString(3, oClienteModel.getA01_endereco());
            stmt.setString(4, oClienteModel.getA01_telefone());
            stmt.setString(5, oClienteModel.getA01_cpf());
            stmt.setDouble(6, oClienteModel.getA01_credito());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
    
    public ClienteModel consultarClientePersistencia(ClienteModel oClienteModel){
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CLIENTE_01 WHERE A01_codigo = ? AND A01_ativo = TRUE")) {

            stmt.setInt(1, oClienteModel.getA01_codigo());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    oClienteModel.setA01_codigo(rs.getInt("A01_codigo"));
                    oClienteModel.setA01_nome(rs.getString("A01_nome"));
                    oClienteModel.setA01_endereco(rs.getString("A01_endereco"));
                    oClienteModel.setA01_telefone(rs.getString("A01_telefone"));
                    oClienteModel.setA01_cpf(rs.getString("A01_cpf"));
                    oClienteModel.setA01_credito(rs.getDouble("A01_credito"));
                    return oClienteModel;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar cliente: " + e.getMessage());
        }
        return null;
    }
    
    public List<ClienteModel> consultarClientesPersistencia () {
        List<ClienteModel> listaClienteModel = new ArrayList<>();
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CLIENTE_01 WHERE A01_ativo = TRUE");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClienteModel objClienteModel = new ClienteModel();
                objClienteModel.setA01_codigo(rs.getInt("A01_codigo"));
                objClienteModel.setA01_nome(rs.getString("A01_nome"));
                objClienteModel.setA01_endereco(rs.getString("A01_endereco"));
                objClienteModel.setA01_telefone(rs.getString("A01_telefone"));
                objClienteModel.setA01_cpf(rs.getString("A01_cpf"));
                objClienteModel.setA01_credito(rs.getDouble("A01_credito"));
                listaClienteModel.add(objClienteModel);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar clientes: " + e.getMessage());
        }
        return listaClienteModel;
    }
}