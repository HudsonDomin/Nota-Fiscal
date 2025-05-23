package com.mycompany.prj_nota.Pck_Persistencia;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ItemModel;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPersistencia {
    public void inserirItemPersistencia(ItemModel oItemModel) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection()) {
            try (CallableStatement stmt = conn.prepareCall("{CALL Proc_InsItem(?, ?, ?, ?)}")) {
                stmt.setInt(1, oItemModel.getA03_codigo());
                stmt.setInt(2, oItemModel.getA02_codigo());
                stmt.setInt(3, oItemModel.getA04_quantidade());
                stmt.setDouble(4, oItemModel.getA04_valorItem());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir item: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public void atualizarItemPersistencia(ItemModel oItemModel) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdItem(?, ?, ?)}")) {

            stmt.setInt(1, oItemModel.getA04_codigo());
            stmt.setInt(2, oItemModel.getA04_quantidade());
            stmt.setDouble(3, oItemModel.getA04_valorItem());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public void deletarItemPersistencia(ItemModel oItemModel) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection()) {
            try (CallableStatement stmt = conn.prepareCall("{CALL Proc_DelItem(?)}")) {
                stmt.setInt(1, oItemModel.getA04_codigo());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar item: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ItemModel consultarItensPersistencia (ItemModel oItemModel) {
        ItemModel item = new ItemModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04 WHERE A04_codigo = ?")) {

            stmt.setInt(1, oItemModel.getA04_codigo());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item.setA04_codigo(rs.getInt("A04_codigo"));
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
    
    public List<ItemModel> consultarItensPersistencia(){
        List<ItemModel> itens = new ArrayList<>();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04");
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemModel item = new ItemModel();
                item.setA04_codigo(rs.getInt("A04_codigo"));
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