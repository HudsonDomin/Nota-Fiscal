package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Model.PedidoModel;
import com.mycompany.prj_nota.Pck_Persistencia.PedidoPersistencia;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoControl {

    public void inserirPedido(double fValorTotal, int iCodCliente) {
        PedidoModel objPedidoModel = new PedidoModel();
        objPedidoModel.setA02_valorTotal(fValorTotal);
        objPedidoModel.setA01_codigo(iCodCliente);
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        objPedidoPersistencia.inserirPedidoPersistencia(objPedidoModel);
    }

    public void removerPedido(int iCodigo) {
        PedidoModel objPedidoModel = new PedidoModel();
        objPedidoModel.setA02_codigo(iCodigo);
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        objPedidoPersistencia.removerPedidoPersistencia(objPedidoModel);
    }

    public void atualizarPedido(int iCodigo, Date dData, double dValorTotal, int iCodCliente) {
        PedidoModel objPedidoModel = new PedidoModel();
        objPedidoModel.setA02_codigo(iCodigo);
        objPedidoModel.setA01_codigo(iCodCliente);
        objPedidoModel.setA02_valorTotal(dValorTotal);
        objPedidoModel.setA02_data(dData);
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        objPedidoPersistencia.atualizarPedidoPersistencia(objPedidoModel);
    }

    public PedidoModel consultarPedido(int iCodigo) {
        PedidoModel objPedidoModel = new PedidoModel();
        objPedidoModel.setA02_codigo(iCodigo);
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        return objPedidoPersistencia.consultarPedidoPersistencia(objPedidoModel);
    }

    public List<PedidoModel> consultarPedidos() {
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        return objPedidoPersistencia.consultarPedidosPersistencia();
    }
}
