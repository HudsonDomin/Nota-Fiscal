/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ClienteModel;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_InsCliente}");
            stmt.setString("V_A01_nome", objClienteModel.getA01_nome());
            stmt.setString("V_A01_endereco", objClienteModel.getA01_endereco());
            stmt.setString("V_A01_cpf", objClienteModel.getA01_cpf());
            stmt.setDouble("V_A01_credito", objClienteModel.getA01_credito());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }
    
    public void removerCliente(int iCodigo){
        objClienteModel.setA01_codigo(iCodigo);
        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_DelCliente}");
            stmt.setInt("V_A01_codigo", objClienteModel.getA01_codigo());
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
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_UpdCliente}");
            stmt.setInt("V_A01_codigo", objClienteModel.getA01_codigo());
            stmt.setString("V_A01_nome", objClienteModel.getA01_nome());
            stmt.setString("V_A01_endereco", objClienteModel.getA01_endereco());
            stmt.setString("V_A01_cpf", objClienteModel.getA01_cpf());
            stmt.setDouble("V_A01_credito", objClienteModel.getA01_credito());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
    
    public void consultarCliente(int iCodigo){
        objClienteModel.setA01_codigo(iCodigo);
    }
    
    public List<ClienteModel> consultarClientes(){
        return new ArrayList<>();
    }
  
}
