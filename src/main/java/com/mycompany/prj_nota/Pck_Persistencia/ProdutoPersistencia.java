package com.mycompany.prj_nota.Pck_Persistencia;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoPersistencia {
    public void inserirProdutoPersistencia(String sDescricao, double dValor, int iEstoque){
        ProdutoModel objProdutoModel = new ProdutoModel();
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValor);
        objProdutoModel.setA03_estoque(iEstoque);

        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsProduto(?, ?, ?)}")) {

            stmt.setString(1, objProdutoModel.getA03_descricao());
            stmt.setDouble(2, objProdutoModel.getA03_valorUnitario());
            stmt.setInt(3, objProdutoModel.getA03_estoque());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }
    
    public void deletarProdutoPersistencia(int iCodigo) {
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_DelProduto(?)}")) {

            stmt.setInt(1, iCodigo);
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
    }
    
    public void atualizarProdutoPersistencia(int iCodigo, String sDescricao, double dValor, int iEstoque) {
        ProdutoModel objProdutoModel = new ProdutoModel();
        objProdutoModel.setA03_codigo(iCodigo);
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValor);
        objProdutoModel.setA03_estoque(iEstoque);

        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdProduto(?, ?, ?, ?)}")) {

            stmt.setInt(1, objProdutoModel.getA03_codigo());
            stmt.setString(2, objProdutoModel.getA03_descricao());
            stmt.setDouble(3, objProdutoModel.getA03_valorUnitario());
            stmt.setInt(4, objProdutoModel.getA03_estoque());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }
    
    public ProdutoModel consultarProdutosPersistencia(int iCodigo) {
        ProdutoModel objProdutoModel = new ProdutoModel();
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUTO_03 WHERE A03_codigo = ?")) {

            stmt.setInt(1, iCodigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    objProdutoModel.setA03_codigo(rs.getInt("A03_codigo"));
                    objProdutoModel.setA03_descricao(rs.getString("A03_descricao"));
                    objProdutoModel.setA03_valorUnitario(rs.getDouble("A03_valorUnitario"));
                    objProdutoModel.setA03_estoque(rs.getInt("A03_estoque"));
                    return objProdutoModel;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar produto: " + e.getMessage());
        }
        return null;
    }
    
    public List<ProdutoModel> consultarProdutosPersistencia() {
        List<ProdutoModel> listaProdutos = new ArrayList<>();
        ConexaoMySql objConexaoMySql = new ConexaoMySql();
        try (Connection conn = objConexaoMySql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUTO_03");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdutoModel objProdutoModel = new ProdutoModel();
                objProdutoModel.setA03_codigo(rs.getInt("A03_codigo"));
                objProdutoModel.setA03_descricao(rs.getString("A03_descricao"));
                objProdutoModel.setA03_valorUnitario(rs.getDouble("A03_valorUnitario"));
                objProdutoModel.setA03_estoque(rs.getInt("A03_estoque"));
                listaProdutos.add(objProdutoModel);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar produtos: " + e.getMessage());
        }
        return listaProdutos;
    }
}