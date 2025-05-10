package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoControl {

    public void inserirProduto(String sDescricao, double dValor, int iEstoque) {
        ProdutoModel produto = new ProdutoModel();
        produto.setA03_descricao(sDescricao);
        produto.setA03_valorUnitario(dValor);
        produto.setA03_estoque(iEstoque);

        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_InsProduto(?, ?, ?)}")) {

            stmt.setString(1, produto.getA03_descricao());
            stmt.setDouble(2, produto.getA03_valorUnitario());
            stmt.setInt(3, produto.getA03_estoque());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public void deletarProduto(int iCodigo) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_DelProduto(?)}")) {

            stmt.setInt(1, iCodigo);
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
    }

    public void atualizarProduto(int iCodigo, String sDescricao, double dValor, int iEstoque) {
        ProdutoModel produto = new ProdutoModel();
        produto.setA03_codigo(iCodigo);
        produto.setA03_descricao(sDescricao);
        produto.setA03_valorUnitario(dValor);
        produto.setA03_estoque(iEstoque);

        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdProduto(?, ?, ?, ?)}")) {

            stmt.setInt(1, produto.getA03_codigo());
            stmt.setString(2, produto.getA03_descricao());
            stmt.setDouble(3, produto.getA03_valorUnitario());
            stmt.setInt(4, produto.getA03_estoque());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public ProdutoModel consultarProduto(int iCodigo) {
        ProdutoModel produto = new ProdutoModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUTO_03 WHERE A03_codigo = ?")) {

            stmt.setInt(1, iCodigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto.setA03_codigo(rs.getInt("A03_codigo"));
                    produto.setA03_descricao(rs.getString("A03_descricao"));
                    produto.setA03_valorUnitario(rs.getDouble("A03_valorUnitario"));
                    produto.setA03_estoque(rs.getInt("A03_estoque"));
                    return produto;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar produto: " + e.getMessage());
        }
        return null;
    }

    public List<ProdutoModel> consultarProdutos() {
        List<ProdutoModel> produtos = new ArrayList<>();
        ConexaoMySql conexao = new ConexaoMySql();
        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUTO_03");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdutoModel produto = new ProdutoModel();
                produto.setA03_codigo(rs.getInt("A03_codigo"));
                produto.setA03_descricao(rs.getString("A03_descricao"));
                produto.setA03_valorUnitario(rs.getDouble("A03_valorUnitario"));
                produto.setA03_estoque(rs.getInt("A03_estoque"));
                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar produtos: " + e.getMessage());
        }
        return produtos;
    }
}
