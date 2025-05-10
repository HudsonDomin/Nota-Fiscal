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

    public void inserirPedido(Date dData, double fValorTotal, int iCodigo) {
        PedidoModel pedido = new PedidoModel();
        pedido.setA02_data(dData);
        pedido.setA02_valorTotal(fValorTotal);
        pedido.setA01_codigo(iCodigo);

        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsPedido(?, ?, ?)}")) {

            stmt.setDate(1, new java.sql.Date(pedido.getA02_data().getTime()));
            stmt.setDouble(2, pedido.getA02_valorTotal());
            stmt.setInt(3, pedido.getA01_codigo());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    public void removerPedido(int iNumero) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_DelPedido(?)}")) {

            stmt.setInt(1, iNumero);
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao remover pedido: " + e.getMessage());
        }
    }

    public void atualizarPedido(int iNumero, Date dData, double dValorTotal, int iCodigo) {
        PedidoModel pedido = new PedidoModel();
        pedido.setA02_codigo(iNumero);
        pedido.setA02_data(dData);
        pedido.setA02_valorTotal(dValorTotal);
        pedido.setA01_codigo(iCodigo);

        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdPedido(?, ?, ?, ?)}")) {

            stmt.setInt(1, pedido.getA02_codigo());
            stmt.setDate(2, new java.sql.Date(pedido.getA02_data().getTime()));
            stmt.setDouble(3, pedido.getA02_valorTotal());
            stmt.setInt(4, pedido.getA01_codigo());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
        }
    }

    public PedidoModel consultarPedido(int iNumero) {
        PedidoModel pedido = new PedidoModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PEDIDO_02 WHERE A02_codigo = ?")) {

            stmt.setInt(1, iNumero);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido.setA02_codigo(rs.getInt("A02_codigo"));
                    pedido.setA02_data(rs.getDate("A02_data"));
                    pedido.setA02_valorTotal(rs.getDouble("A02_valorTotal"));
                    pedido.setA01_codigo(rs.getInt("A01_codigo"));
                    return pedido;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar pedido: " + e.getMessage());
        }
        return null;
    }

    public List<PedidoModel> consultarPedidos() {
        List<PedidoModel> pedidos = new ArrayList<>();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PEDIDO_02");
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setA02_codigo(rs.getInt("A02_codigo"));
                pedido.setA02_data(rs.getDate("A02_data"));
                pedido.setA02_valorTotal(rs.getDouble("A02_valorTotal"));
                pedido.setA01_codigo(rs.getInt("A01_codigo"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar pedidos: " + e.getMessage());
        }
        return pedidos;
    }
}
