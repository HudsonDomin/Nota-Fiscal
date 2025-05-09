package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ItemModel;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemControl {

    public void inserirItem(int iCodProduto, int iCodPedido, int iQuantidade, double dValorItem) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (CallableStatement stmt1 = conn.prepareCall("{CALL Proc_InsItem(?, ?, ?, ?)}")) {
                stmt1.setInt(1, iCodProduto);
                stmt1.setInt(2, iCodPedido);
                stmt1.setInt(3, iQuantidade);
                stmt1.setDouble(4, dValorItem);
                stmt1.execute();
            }

            try (CallableStatement stmt2 = conn.prepareCall("{CALL Proc_UpdValorTotal(?)}")) {
                stmt2.setInt(1, iCodPedido);
                stmt2.execute();
            }

            try(CallableStatement stmt3 = conn.prepareCall("{CALL Proc_UpdProduto(?, ?, ?, ?)}")){
                ProdutoControl objProdutoControl = new ProdutoControl();
                ProdutoModel objProdutoModel = objProdutoControl.consultarProduto(iCodProduto);
                int quantidadeRestante = objProdutoModel.getA03_estoque() - iQuantidade;
                if(quantidadeRestante < 0){
                    throw new IllegalArgumentException("Sem estoque o suficiente");
                }
                stmt3.setInt(1, objProdutoModel.getA03_codigo());
                stmt3.setString(2, objProdutoModel.getA03_descricao());
                stmt3.setDouble(3, objProdutoModel.getA03_valorUnitario());
                stmt3.setInt(4, objProdutoModel.getA03_estoque() - iQuantidade);
                stmt3.execute();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                System.out.println("Erro, desfazendo transação: " + e.getMessage());
                conexao.getConnection().rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao fazer rollback: " + rollbackEx.getMessage());
            }
        } catch (IllegalArgumentException e){
            System.out.println("Erro ao criar item: " + e.getMessage());
        }
    }


    public void atualizarItem(int iCodItem, int iCodProduto, int iCodPedido, int iQuantidade, double dValorItem) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdItem(?, ?, ?, ?, ?)}")) {

            stmt.setInt(1, iCodItem);
            stmt.setInt(2, iCodProduto);
            stmt.setInt(3, iCodPedido);
            stmt.setInt(4, iQuantidade);
            stmt.setDouble(5, dValorItem);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item: " + e.getMessage());
        }
    }

    public void deletarItem(int id) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_Delitem(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao remover item: " + e.getMessage());
        }
    }

    public ItemModel consultarItem(int iCodItem) {
        ItemModel item = new ItemModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04 WHERE A04_id = ?")) {

            stmt.setInt(1, iCodItem);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item.setA04_codigo(rs.getInt("A04_id"));
                    item.setA03_codigo(rs.getInt("A03_codigo"));
                    item.setA02_codigo(rs.getInt("A02_codigo"));
                    item.setA04_quantidade(rs.getInt("A04_quantidade"));
                    item.setA04_valorItem(rs.getDouble("A04_valorItem"));
                    return item;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar item: " + e.getMessage());
        }
        return null;
    }

    public List<ItemModel> consultarItens() {
        List<ItemModel> itens = new ArrayList<>();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04");
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemModel item = new ItemModel();
                item.setA04_codigo(rs.getInt("A04_id"));
                item.setA03_codigo(rs.getInt("A03_codigo"));
                item.setA02_codigo(rs.getInt("A02_codigo"));
                item.setA04_quantidade(rs.getInt("A04_quantidade"));
                item.setA04_valorItem(rs.getDouble("A04_valorItem"));
                itens.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar itens: " + e.getMessage());
        }
        return itens;
    }
}
