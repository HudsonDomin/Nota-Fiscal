package com.mycompany.prj_nota.Pck_Persistencia;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.PedidoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoPersistencia {
    public void inserirPedidoPersistencia(PedidoModel oPedidoModel) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsPedido(?, ?)}")) {
            stmt.setDouble(1, oPedidoModel.getA02_valorTotal());
            stmt.setInt(2, oPedidoModel.getA01_codigo());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }
    
    public void removerPedidoPersistencia(PedidoModel oPedidoModel) {
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (var conn = objConexaoMySql.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_DelPedido(?)}")) {

            stmt.setInt(1, oPedidoModel.getA02_codigo());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao remover pedido: " + e.getMessage());
        }
    }
    
    public void atualizarPedidoPersistencia(PedidoModel oPedidoModel) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdPedido(?, ?, ?, ?)}")) {

            stmt.setInt(1, oPedidoModel.getA02_codigo());
            stmt.setDate(2, new Date(oPedidoModel.getA02_data().getTime()));
            stmt.setDouble(3, oPedidoModel.getA02_valorTotal());
            stmt.setInt(4, oPedidoModel.getA01_codigo());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
        }
    }
    
    public PedidoModel consultarPedidoPersistencia(PedidoModel oPedidoModel) {
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (var conn = objConexaoMySql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PEDIDO_02 WHERE A02_codigo = ?")) {

            stmt.setInt(1, oPedidoModel.getA02_codigo());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    oPedidoModel.setA02_data(rs.getDate("A02_data"));
                    oPedidoModel.setA02_valorTotal(rs.getDouble("A02_valorTotal"));
                    oPedidoModel.setA01_codigo(rs.getInt("A01_codigo"));
                    return oPedidoModel;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar pedido: " + e.getMessage());
        }
        return null;
    }
    public List<PedidoModel> consultarPedidosPersistencia() {
        List<PedidoModel> listaPedidos = new ArrayList<>();
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (var conn = objConexaoMySql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PEDIDO_02");
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                PedidoModel objPedidoModel = new PedidoModel();
                objPedidoModel.setA02_codigo(rs.getInt("A02_codigo"));
                objPedidoModel.setA02_data(rs.getDate("A02_data"));
                objPedidoModel.setA02_valorTotal(rs.getDouble("A02_valorTotal"));
                objPedidoModel.setA01_codigo(rs.getInt("A01_codigo"));
                listaPedidos.add(objPedidoModel);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar pedidos: " + e.getMessage());
        }
        return listaPedidos;
    }
}