

package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.PedidoModel;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoControl {

    /*int a01_codigo; String a01_nome; String a01_endereco; String a01_cpf; double a01_credito;*/
    PedidoModel objPedidoModel = new PedidoModel();
    ConexaoMySql objConexaoMySql = new ConexaoMySql();

    public PedidoControl(){
        objConexaoMySql.getConnection();
    }

    //=========== INSERIR PEDIDO ===============================================================================================================================================
    public void inserirPedido(Date dateData, double fValorTotal, int iCodigo) {
        objPedidoModel.setA02_data(dateData);
        objPedidoModel.setA02_valorTotal(fValorTotal);
        objPedidoModel.setA01_codigo(iCodigo);
        try {
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_InsPedido(?, ?, ?)}");
            stmt.setDate(1, (java.sql.Date) objPedidoModel.getA02_data());
            stmt.setDouble(2, objPedidoModel.getA02_valorTotal());
            stmt.setInt(3, objPedidoModel.getA01_codigo());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }
    //============= REMOVER PEDIDO =======================================================
    public void removerPedido(int iNumero){
        objPedidoModel.setA01_codigo(iNumero);
        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_DelPedido(?)}");
            stmt.setInt(1, objPedidoModel.getA02_codigo());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao remover pedido: " + e.getMessage());
        }
    }

    //============== ATUALIZAR PEDIDO =====================================================
    public void atualizarPedido(int iNumero, Date dateData, double dValorTotal, int iCodigo){

        objPedidoModel.setA02_codigo(iNumero);
        objPedidoModel.setA02_data(dateData);
        objPedidoModel.setA02_valorTotal(dValorTotal);
        objPedidoModel.setA01_codigo(iCodigo);


        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_UpdPedido(?, ?, ?, ?)}");
            stmt.setInt(1, objPedidoModel.getA02_codigo());
            stmt.setDate(2, (java.sql.Date) objPedidoModel.getA02_data());
            stmt.setDouble(3, objPedidoModel.getA02_valorTotal());
            stmt.setInt(4, objPedidoModel.getA01_codigo());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
        }
    }

    //=========== CONSULTA DO PEDIDO ===================================================
    public PedidoModel consultarPedido(int iNumero){
        objPedidoModel.setA02_codigo(iNumero);
        try{
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM PEDIDO_02 WHERE A02_numero = ?");
            stmt.setInt(1, objPedidoModel.getA02_codigo());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){


                objPedidoModel.setA02_data(rs.getDate("A02_data"));
                objPedidoModel.setA02_valorTotal(rs.getFloat("A02_valorTotal"));
                objPedidoModel.setA01_codigo(rs.getInt("A01_codigo"));

                return objPedidoModel;
            }
        }catch (SQLException e){
            System.out.println("Erro ao consultar pedido: " + e.getMessage());
        }
        return null;
    }

    public List<PedidoModel> consultarPedidos(){
        List<PedidoModel> pedidos = new ArrayList<>();
        try{
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM PEDIDO_02");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                PedidoModel objPedidoModel = new PedidoModel();
                objPedidoModel.setA02_data(rs.getDate("A02_data"));
                objPedidoModel.setA02_valorTotal(rs.getFloat("A02_valorTotal"));
                objPedidoModel.setA01_codigo(rs.getInt("A01_codigo"));
                pedidos.add(objPedidoModel);
            }
        }catch (SQLException e){
            System.out.println("Erro ao consultar pedidos: " + e.getMessage());
        }

        return pedidos;
    }
}
