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
    public void inserirClientePersistencia(ClienteModel objClienteModel){
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsCliente(?, ?, ?, ?, ?)}")) {

            stmt.setString(1, objClienteModel.getA01_nome());
            stmt.setString(2, objClienteModel.getA01_endereco());
            stmt.setString(3, objClienteModel.getA01_telefone());
            stmt.setString(4, objClienteModel.getA01_cpf());
            stmt.setDouble(5, objClienteModel.getA01_credito());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }
    public void removerClientePersistencia(int iCodigo){
    ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_DelCliente(?)}")) {

            stmt.setInt(1, iCodigo);
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }
    }
    
    public void atualizarClientePersistencia(ClienteModel objClienteModel){
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdCliente(?, ?, ?, ?, ?, ?)}")) {

            stmt.setInt(1, objClienteModel.getA01_codigo());
            stmt.setString(2, objClienteModel.getA01_nome());
            stmt.setString(3, objClienteModel.getA01_endereco());
            stmt.setString(4, objClienteModel.getA01_telefone());
            stmt.setString(5, objClienteModel.getA01_cpf());
            stmt.setDouble(6, objClienteModel.getA01_credito());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
    
    public ClienteModel consultarClientePersistencia(int iCodigo){
        ClienteModel objClienteModel = new ClienteModel();
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CLIENTE_01 WHERE A01_codigo = ? AND A01_ativo = TRUE")) {

            stmt.setInt(1, iCodigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    objClienteModel.setA01_codigo(rs.getInt("A01_codigo"));
                    objClienteModel.setA01_nome(rs.getString("A01_nome"));
                    objClienteModel.setA01_endereco(rs.getString("A01_endereco"));
                    objClienteModel.setA01_telefone(rs.getString("A01_telefone"));
                    objClienteModel.setA01_cpf(rs.getString("A01_cpf"));
                    objClienteModel.setA01_credito(rs.getDouble("A01_credito"));
                    return objClienteModel;
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
                ClienteModel cliente = new ClienteModel();
                cliente.setA01_codigo(rs.getInt("A01_codigo"));
                cliente.setA01_nome(rs.getString("A01_nome"));
                cliente.setA01_endereco(rs.getString("A01_endereco"));
                cliente.setA01_telefone(rs.getString("A01_telefone"));
                cliente.setA01_cpf(rs.getString("A01_cpf"));
                cliente.setA01_credito(rs.getDouble("A01_credito"));
                listaClienteModel.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar clientes: " + e.getMessage());
        }
        return listaClienteModel;
    }
}