/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ClienteModel;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author lab03aluno
 */
public class ClienteControl {
    
    ClienteModel objClienteModel = new ClienteModel();
    ConexaoMySql objConexaoMySql = new ConexaoMySql();

    public ClienteControl(){
        objConexaoMySql.getConnection();
    }
    
    public void inserirCliente(String sNome, String sEndereco,String sTelefone, String sCpf, double dCredito ){
        objClienteModel.setA01_nome(sNome);
        objClienteModel.setA01_endereco(sEndereco);
        objClienteModel.setA01_telefone(sTelefone);
        objClienteModel.setA01_cpf(sCpf);
        objClienteModel.setA01_credito(dCredito);

        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_InsCliente(?, ?, ?, ?, ?)}");
            stmt.setString(1, objClienteModel.getA01_nome());
            stmt.setString(2, objClienteModel.getA01_endereco());
            stmt.setString(3, objClienteModel.getA01_telefone());
            stmt.setString(4, objClienteModel.getA01_cpf());
            stmt.setDouble(5, objClienteModel.getA01_credito());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }
    
    public void removerCliente(int iCodigo){
        objClienteModel.setA01_codigo(iCodigo);
        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_DelCliente(?)}");
            stmt.setInt(1, objClienteModel.getA01_codigo());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }
    }
    
    public void atualizarCliente(int iCodigo, String sNome, String sEndereco,String sTelefone, String sCpf, double fCredito ){
        
        objClienteModel.setA01_codigo(iCodigo);
        objClienteModel.setA01_nome(sNome);
        objClienteModel.setA01_endereco(sEndereco);
        objClienteModel.setA01_telefone(sTelefone);
        objClienteModel.setA01_cpf(sCpf);
        objClienteModel.setA01_credito(fCredito);

        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_UpdCliente(?, ?, ?, ?, ?, ?)}");
            stmt.setInt(1, objClienteModel.getA01_codigo());
            stmt.setString(2, objClienteModel.getA01_nome());
            stmt.setString(3, objClienteModel.getA01_endereco());
            stmt.setString(4, objClienteModel.getA01_telefone());
            stmt.setString(5, objClienteModel.getA01_cpf());
            stmt.setDouble(6, objClienteModel.getA01_credito());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
    
    public ClienteModel consultarCliente(int iCodigo){
        objClienteModel.setA01_codigo(iCodigo);
        try{
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM CLIENTE_01 WHERE A01_id = ?");
            stmt.setInt(1, objClienteModel.getA01_codigo());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                objClienteModel.setA01_nome(rs.getString("A01_nome"));
                objClienteModel.setA01_cpf(rs.getString("A01_cpf"));
                objClienteModel.setA01_endereco(rs.getString("A01_endereco"));
                objClienteModel.setA01_telefone(rs.getString("A01_telefone"));
                objClienteModel.setA01_credito(rs.getDouble("A01_credito"));
                return objClienteModel;
            }
        }catch (SQLException e){
            System.out.println("Erro ao consultar: " + e.getMessage());
        }
        return null;
    }
    
    public List<ClienteModel> consultarClientes(){
        List<ClienteModel> clientes = new ArrayList<>();
        try{
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM CLIENTE_01");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ClienteModel objClienteModel = new ClienteModel();
                objClienteModel.setA01_codigo(rs.getInt("A01_codigo"));
                objClienteModel.setA01_nome(rs.getString("A01_nome"));
                objClienteModel.setA01_cpf(rs.getString("A01_cpf"));
                objClienteModel.setA01_endereco(rs.getString("A01_endereco"));
                objClienteModel.setA01_telefone(rs.getString("A01_telefone"));
                objClienteModel.setA01_credito(rs.getDouble("A01_credito"));
                clientes.add(objClienteModel);
            }
        }catch (SQLException e){
            System.out.println("Erro ao consultar: " + e.getMessage());
        }

        return clientes;
    }
  
}
