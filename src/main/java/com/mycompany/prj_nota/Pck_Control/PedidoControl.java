package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Model.PedidoModel;

import java.util.Date;

public class PedidoControl {
    PedidoModel objPedidoModel = new PedidoModel();

    public void inserirPedido(int iCodigo, Date dData,double dValorTotal, int iClienteCodigo) {
        objPedidoModel.setA02_codigo(iCodigo);
        objPedidoModel.setA02_data(dData);
        objPedidoModel.setA02_valorTotal(dValorTotal);
        objPedidoModel.setA01_codigo(iClienteCodigo);
    }

    public void excluirPedido(int iCodigo) {
        objPedidoModel.setA02_codigo(iCodigo);
    }

    public void atualizarPedido(int iCodigo, Date dData,double dValorTotal, int iClienteCodigo) {
        objPedidoModel.setA02_codigo(iCodigo);
        objPedidoModel.setA02_data(dData);
        objPedidoModel.setA02_valorTotal(dValorTotal);
        objPedidoModel.setA01_codigo(iClienteCodigo);
    }

    public void consultarPedido(int iCodigo) {
        objPedidoModel.setA02_codigo(iCodigo);
    }
}
