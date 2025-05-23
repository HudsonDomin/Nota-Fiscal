package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;
import com.mycompany.prj_nota.Pck_Persistencia.ProdutoPersistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoControl {

    public void inserirProduto(String sDescricao, double dValor, int iEstoque) {
        ProdutoModel objProdutoModel = new ProdutoModel();
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValor);
        objProdutoModel.setA03_estoque(iEstoque);
        ProdutoPersistencia  objProdutoPersistencia = new ProdutoPersistencia();
        objProdutoPersistencia.inserirProdutoPersistencia(objProdutoModel);
    }

    public void deletarProduto(int iCodigo) {
        ProdutoModel objProdutoModel = new ProdutoModel();
        objProdutoModel.setA03_codigo(iCodigo);
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        objProdutoPersistencia.deletarProdutoPersistencia(objProdutoModel);
    }

    public void atualizarProduto(int iCodigo, String sDescricao, double dValor, int iEstoque) {
        ProdutoModel objProdutoModel = new ProdutoModel();
        objProdutoModel.setA03_codigo(iCodigo);
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValor);
        objProdutoModel.setA03_estoque(iEstoque);
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        objProdutoPersistencia.atualizarProdutoPersistencia(objProdutoModel);
    }

    public ProdutoModel consultarProduto(int iCodigo) {
        ProdutoModel objProdutoModel = new ProdutoModel();
        objProdutoModel.setA03_codigo(iCodigo);
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        return objProdutoPersistencia.consultarProdutosPersistencia(objProdutoModel);
    }

    public List<ProdutoModel> consultarProdutos() {
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        return objProdutoPersistencia.consultarProdutosPersistencia();
    }
}
