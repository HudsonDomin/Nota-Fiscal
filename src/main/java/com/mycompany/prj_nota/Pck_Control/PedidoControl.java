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

    public void inserirPedido(double fValorTotal, int iCodigo) {
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        objPedidoPersistencia.inserirPedidoPersistencia(fValorTotal, iCodigo);
    }

    public void removerPedido(int iNumero) {
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        objPedidoPersistencia.removerPedidoPersistencia(iNumero);
    }

    public void atualizarPedido(int iNumero, Date dData, double dValorTotal, int iCodigo) {
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        objPedidoPersistencia.atualizarPedidoPersistencia(iNumero, dData, dValorTotal, iCodigo);
    }

    public PedidoModel consultarPedido(int iNumero) {
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        objPedidoPersistencia.consultarPedidoPersistencia(iNumero);
        return null;
    }

    public List<PedidoModel> consultarPedidos() {
        PedidoPersistencia objPedidoPersistencia = new PedidoPersistencia();
        return objPedidoPersistencia.consultarPedidosPersistencia();
    }
}
