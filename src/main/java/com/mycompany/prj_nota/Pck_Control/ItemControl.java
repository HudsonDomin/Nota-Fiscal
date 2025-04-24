package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ItemModel;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemControl {
    ItemModel objItemModel = new ItemModel();
    ConexaoMySql objConexaoMySql = new ConexaoMySql();

    public ItemControl(){
        objConexaoMySql.getConnection();
    }

    public void inserirItem(int iProdutoCodigo, int iPedidoCodigo, int iQuantidade, double dValorTotal) {
        //objItemModel.setA04_codigo(iCodigo);
        objItemModel.setA03_codigo(iProdutoCodigo);
        objItemModel.setA02_codigo(iPedidoCodigo);
        objItemModel.setA04_quantidade(iQuantidade);
        objItemModel.setA04_valorItem(dValorTotal);

        try {
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_InsItem(?, ?, ?, ?)}");
            stmt.setInt(1, objItemModel.getA03_codigo());
            stmt.setInt(2, objItemModel.getA02_codigo());
            stmt.setInt(3, objItemModel.getA04_quantidade());
            stmt.setDouble(4, objItemModel.getA04_valorItem());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    public void removerItem(int iCodigo) {
        objItemModel.setA04_codigo(iCodigo);
        try {
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_Delitem(?)}");
            stmt.setInt(1, objItemModel.getA04_codigo());
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao remover Item: " + e.getMessage());
        }
    }

    public void atualizarItem(int iCodigo, int iProdutoCodigo, int iPedidoCodigo, int iQuantidade, double dValorTotal) {
        objItemModel.setA04_codigo(iCodigo);
        objItemModel.setA03_codigo(iProdutoCodigo);
        objItemModel.setA02_codigo(iPedidoCodigo);
        objItemModel.setA04_quantidade(iQuantidade);
        objItemModel.setA04_valorItem(dValorTotal);

        try {
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_UpdItem(?, ?, ?, ?, ?)}");
            stmt.setInt(1, objItemModel.getA04_codigo());
            stmt.setInt(2, objItemModel.getA03_codigo());
            stmt.setInt(3, objItemModel.getA02_codigo());
            stmt.setInt(4, objItemModel.getA04_quantidade());
            stmt.setDouble(5, objItemModel.getA04_valorItem());
            stmt.executeQuery();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public ItemModel consultarItem(int iCodigo) {
        objItemModel.setA04_codigo(iCodigo);
        try {
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM ITEM_04 WHERE A04_id = ?");
            stmt.setInt(1, objItemModel.getA04_codigo());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                objItemModel.setA03_codigo(rs.getInt("A03_codigo"));
                objItemModel.setA02_codigo(rs.getInt("A02_codigo"));
                objItemModel.setA04_quantidade(rs.getInt("A04_quantidade"));
                objItemModel.setA04_valorItem(rs.getDouble("A04_valorItem"));
                return objItemModel;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar" + e.getMessage());
        }
        return null;
    }

    public List<ItemModel> consultarItem() {
        List<ItemModel> itens = new ArrayList<>();
        try {
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM ITEM_04");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemModel objItemModel = new ItemModel();
                objItemModel.setA03_codigo(rs.getInt("A03_codigo"));
                objItemModel.setA02_codigo(rs.getInt("A02_codigo"));
                objItemModel.setA04_quantidade(rs.getInt("A04_quantidade"));
                objItemModel.setA04_valorItem(rs.getDouble("A04_valorItem"));
                itens.add(objItemModel);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar: " + e.getMessage());
        }
        return itens;
    }
}
