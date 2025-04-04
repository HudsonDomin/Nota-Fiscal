/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Model.ClienteModel;
/**
 *
 * @author lab03aluno
 */
public class ClienteControl {
    
    ClienteModel objClienteModel = new ClienteModel();
    
    public void inserirCliente(String sNome, String sEndereco,String sTelefone, String sCpf, float fCredito ){
        
        objClienteModel.setA01_nome(sNome);
        objClienteModel.setA01_endereco(sEndereco);
        objClienteModel.setA01_telefone(sTelefone);
        objClienteModel.setA01_cpf(sCpf);
        objClienteModel.setA01_credito(fCredito);
        
    }
    
    public void removerCliente(int iCodigo){
        objClienteModel.setA01_codigo(iCodigo);
    }
    
    public void atualizarCliente(int iCodigo, String sNome, String sEndereco,String sTelefone, String sCpf, float fCredito ){
        
        objClienteModel.setA01_codigo(iCodigo);
        objClienteModel.setA01_nome(sNome);
        objClienteModel.setA01_endereco(sEndereco);
        objClienteModel.setA01_telefone(sTelefone);
        objClienteModel.setA01_cpf(sCpf);
        objClienteModel.setA01_credito(fCredito);
        
    }
    
    public void consultarCliente(int iCodigo){
        
        objClienteModel.setA01_codigo(iCodigo);
        
    }
  
}
