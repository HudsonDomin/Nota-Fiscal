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
        ProdutoPersistencia  objProdutoPersistencia = new ProdutoPersistencia();
        objProdutoPersistencia.inserirProdutoPersistencia(sDescricao, dValor, iEstoque);
    }

    public void deletarProduto(int iCodigo) {
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        objProdutoPersistencia.deletarProdutoPersistencia(iCodigo);
    }

    public void atualizarProduto(int iCodigo, String sDescricao, double dValor, int iEstoque) {
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        objProdutoPersistencia.atualizarProdutoPersistencia(iCodigo, sDescricao, dValor, iEstoque);
    }

    public ProdutoModel consultarProduto(int iCodigo) {
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        objProdutoPersistencia.consultarProdutosPersistencia(iCodigo);
        return null;
    }

    public List<ProdutoModel> consultarProdutos() {
        ProdutoPersistencia objProdutoPersistencia = new ProdutoPersistencia();
        return objProdutoPersistencia.consultarProdutosPersistencia();
    }
}
