package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ClienteModel;
import com.mycompany.prj_nota.Pck_Persistencia.ClientePersistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteControl {
    
    public void inserirCliente(String sNome, String sEndereco, String sTelefone, String sCpf, double dCredito) {
        ClienteModel objClienteModel = new ClienteModel();
        ClientePersistencia objClientePersistencia = new ClientePersistencia();
        objClienteModel.setA01_nome(sNome);
        objClienteModel.setA01_endereco(sEndereco);
        objClienteModel.setA01_telefone(sTelefone);
        objClienteModel.setA01_cpf(sCpf);
        objClienteModel.setA01_credito(dCredito);
        
        objClientePersistencia.inserirClientePersistencia(objClienteModel);
        
    }

    public void removerCliente(int iCodigo) {
        ClientePersistencia objClientePersistencia = new ClientePersistencia();
            objClientePersistencia.removerClientePersistencia(iCodigo);
    }

    public void atualizarCliente(int iCodigo, String sNome, String sEndereco, String sTelefone, String sCpf, double fCredito) {
        ClienteModel objClienteModel = new ClienteModel();
        objClienteModel.setA01_codigo(iCodigo);
        objClienteModel.setA01_nome(sNome);
        objClienteModel.setA01_endereco(sEndereco);
        objClienteModel.setA01_telefone(sTelefone);
        objClienteModel.setA01_cpf(sCpf);
        objClienteModel.setA01_credito(fCredito);

        ClientePersistencia objClientePersistencia = new ClientePersistencia();
        objClientePersistencia.atualizarClientePersistencia(objClienteModel);
    }

    public ClienteModel consultarCliente(int iCodigo) {
        ClientePersistencia objClientePersistencia = new ClientePersistencia();
        objClientePersistencia.consultarClientePersistencia(iCodigo);
        return null;
    }

    public List<ClienteModel> consultarClientes() {
        ClientePersistencia objClientePersistencia = new ClientePersistencia();
        return objClientePersistencia.consultarClientesPersistencia();
    }
}
