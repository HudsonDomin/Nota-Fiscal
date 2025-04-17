package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lab03aluno
 */
public class ProdutoControl {
    ProdutoModel objProdutoModel = new ProdutoModel();
    ConexaoMySql objConexaoMySql = new ConexaoMySql();

    public ProdutoControl(){
        objConexaoMySql.getConnection();
    }
    public void inserirProduto(/*int iCodigo,*/ String sDescricao, double dValorUnitario, int iEstoque){
        /*objProdutoModel.setA03_codigo(iCodigo);*/
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValorUnitario);
        objProdutoModel.setA03_estoque(iEstoque);
        
        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_InsProduto}");
            stmt.setString(1, objProdutoModel.getA03_descricao());
            stmt.setDouble(2, objProdutoModel.getA03_valorUnitario());
            stmt.setInt(3, objProdutoModel.getA03_estoque());
            stmt.executeQuery();
        }catch (SQLException e){
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    public void atualizarProduto(int iCodigo, String sDescricao, double dValorUnitario, int iEstoque){
        objProdutoModel.setA03_codigo(iCodigo);
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValorUnitario);
        objProdutoModel.setA03_estoque(iEstoque);
        
        try{
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_UpdProduto(?, ?, ?, ?)}");
            stmt.setInt(1,objProdutoModel.getA03_codigo());
            stmt.setString(2, objProdutoModel.getA03_descricao());
            stmt.setDouble(3, objProdutoModel.getA03_valorUnitario());
            stmt.setInt(4, objProdutoModel.getA03_estoque());
            stmt.executeQuery();
        }catch(SQLException e){
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void deletarProduto(int iCodigo){
        objProdutoModel.setA03_codigo(iCodigo);
        
        try {
            CallableStatement stmt = objConexaoMySql.conn.prepareCall("{CALL Proc_DelProduto(?)}");
            stmt.setInt(1, objProdutoModel.getA03_codigo());
            stmt.executeQuery();
        } catch(SQLException e){
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
    }

    public void consultarProduto(int iCodigo){
        objProdutoModel.setA03_codigo(iCodigo);
        
        try{
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM Produto_03 WHERE A03_codigo = ?");
            stmt.setInt(1, objProdutoModel.getA03_codigo());
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                objProdutoModel.setA03_descricao(rs.getString("A03_descricao"));
                objProdutoModel.setA03_valorUnitario(rs.getDouble("A03_valorUnitario"));
                objProdutoModel.setA03_estoque(rs.getInt("A03_estoque"));
                return objProdutoModel;
            }
        } catch(SQLException e) {
            System.out.println("Erro ao consultar: " + e.getMessage());
        }
        return null;
    }
    public List<ProdutoModel> consultarProdutos(){
        List<ProdutoModel> produtos = new ArrayList<>();
        try {
            PreparedStatement stmt = objConexaoMySql.conn.prepareStatement("SELECT * FROM Produto_03");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ProdutoModel objProdutoModel = new ProdutoModel();
                objProdutoModel.setA03_descricao(rs.getString("A03_descricao"));
                objProdutoModel.setA03_valorUnitario(rs.getDouble("A03_valorUnitario"));
                objProdutoModel.setA03_estoque(rs.getInt("A03_estoque"));
                produtos.add(objProdutoModel);
            }
        } catch (SQLException e){
            System.out.println("Erro ao consultar: " + e.getMessage());
        }
        return produtos;
    }
}
