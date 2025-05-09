package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ClienteModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteControl {

    public void inserirCliente(String sNome, String sEndereco, String sTelefone, String sCpf, double dCredito) {
        ClienteModel cliente = new ClienteModel();
        cliente.setA01_nome(sNome);
        cliente.setA01_endereco(sEndereco);
        cliente.setA01_telefone(sTelefone);
        cliente.setA01_cpf(sCpf);
        cliente.setA01_credito(dCredito);

        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsCliente(?, ?, ?, ?, ?)}")) {

            stmt.setString(1, cliente.getA01_nome());
            stmt.setString(2, cliente.getA01_endereco());
            stmt.setString(3, cliente.getA01_telefone());
            stmt.setString(4, cliente.getA01_cpf());
            stmt.setDouble(5, cliente.getA01_credito());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    public void removerCliente(int iCodigo) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_DelCliente(?)}")) {

            stmt.setInt(1, iCodigo);
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }
    }

    public void atualizarCliente(int iCodigo, String sNome, String sEndereco, String sTelefone, String sCpf, double fCredito) {
        ClienteModel cliente = new ClienteModel();
        cliente.setA01_codigo(iCodigo);
        cliente.setA01_nome(sNome);
        cliente.setA01_endereco(sEndereco);
        cliente.setA01_telefone(sTelefone);
        cliente.setA01_cpf(sCpf);
        cliente.setA01_credito(fCredito);

        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdCliente(?, ?, ?, ?, ?, ?)}")) {

            stmt.setInt(1, cliente.getA01_codigo());
            stmt.setString(2, cliente.getA01_nome());
            stmt.setString(3, cliente.getA01_endereco());
            stmt.setString(4, cliente.getA01_telefone());
            stmt.setString(5, cliente.getA01_cpf());
            stmt.setDouble(6, cliente.getA01_credito());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public ClienteModel consultarCliente(int iCodigo) {
        ClienteModel cliente = new ClienteModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CLIENTE_01 WHERE A01_codigo = ?")) {

            stmt.setInt(1, iCodigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente.setA01_codigo(rs.getInt("A01_codigo"));
                    cliente.setA01_nome(rs.getString("A01_nome"));
                    cliente.setA01_endereco(rs.getString("A01_endereco"));
                    cliente.setA01_telefone(rs.getString("A01_telefone"));
                    cliente.setA01_cpf(rs.getString("A01_cpf"));
                    cliente.setA01_credito(rs.getDouble("A01_credito"));
                    return cliente;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar cliente: " + e.getMessage());
        }
        return null;
    }

    public List<ClienteModel> consultarClientes() {
        List<ClienteModel> clientes = new ArrayList<>();
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CLIENTE_01");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClienteModel cliente = new ClienteModel();
                cliente.setA01_codigo(rs.getInt("A01_codigo"));
                cliente.setA01_nome(rs.getString("A01_nome"));
                cliente.setA01_endereco(rs.getString("A01_endereco"));
                cliente.setA01_telefone(rs.getString("A01_telefone"));
                cliente.setA01_cpf(rs.getString("A01_cpf"));
                cliente.setA01_credito(rs.getDouble("A01_credito"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar clientes: " + e.getMessage());
        }
        return clientes;
    }
}
