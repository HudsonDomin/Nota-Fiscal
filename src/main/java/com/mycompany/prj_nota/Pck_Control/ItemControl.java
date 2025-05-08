package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ItemModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemControl {

    public void inserirItem(int codProduto, int codPedido, int quantidade, double valorItem) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsItem(?, ?, ?, ?)}")) {

            stmt.setInt(1, codProduto);
            stmt.setInt(2, codPedido);
            stmt.setInt(3, quantidade);
            stmt.setDouble(4, valorItem);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir item: " + e.getMessage());
        }
    }

    public void atualizarItem(int id, int codProduto, int codPedido, int quantidade, double valorItem) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdItem(?, ?, ?, ?, ?)}")) {

            stmt.setInt(1, id);
            stmt.setInt(2, codProduto);
            stmt.setInt(3, codPedido);
            stmt.setInt(4, quantidade);
            stmt.setDouble(5, valorItem);
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

    public ItemModel consultarItem(int id) {
        ItemModel item = new ItemModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04 WHERE A04_id = ?")) {

            stmt.setInt(1, id);
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
