package com.mycompany.prj_nota.Pck_Persistencia;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ItemModel;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPersistencia {
    public void inserirItemPersistencia(int iCodProduto, int iCodPedido, int iQuantidade, double dValorItem){
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection()) {
            try (CallableStatement stmt = conn.prepareCall("{CALL Proc_InsItem(?, ?, ?, ?)}")) {
                stmt.setInt(1, iCodProduto);
                stmt.setInt(2, iCodPedido);
                stmt.setInt(3, iQuantidade);
                stmt.setDouble(4, dValorItem);
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir item: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public void atualizarItemPersistencia(int iCodItem, int iQuantidade, double dValorItem) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdItem(?, ?, ?)}")) {

            stmt.setInt(1, iCodItem);
            stmt.setInt(2, iQuantidade);
            stmt.setDouble(3, dValorItem);
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item: " + e.getMessage());
        }
    }
    
    public void deletarItemPersistencia(int iCodItem){
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection()) {
            try (CallableStatement stmt = conn.prepareCall("{CALL Proc_DelItem(?)}")) {
                stmt.setInt(1, iCodItem);
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar item: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ItemModel consultarItensPersistencia (int iCodItem) {
        ItemModel item = new ItemModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04 WHERE A04_codigo = ?")) {

            stmt.setInt(1, iCodItem);
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