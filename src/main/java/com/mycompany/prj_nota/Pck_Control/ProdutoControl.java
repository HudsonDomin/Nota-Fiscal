package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Model.ProdutoModel;

public class ProdutoControl {
    ProdutoModel objProdutoModel = new ProdutoModel();

    public void inserirProduto(int iCodigo, String sDescricao, double dValorUnitario, int iEstoque){
        objProdutoModel.setA03_codigo(iCodigo);
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValorUnitario);
        objProdutoModel.setA03_estoque(iEstoque);
    }

    public void atualizarProduto(int iCodigo, String sDescricao, double dValorUnitario, int iEstoque){
        objProdutoModel.setA03_codigo(iCodigo);
        objProdutoModel.setA03_descricao(sDescricao);
        objProdutoModel.setA03_valorUnitario(dValorUnitario);
        objProdutoModel.setA03_estoque(iEstoque);
    }

    public void deletarProduto(int iCodigo){
        objProdutoModel.setA03_codigo(iCodigo);
    }

    public void consultarProduto(int iCodigo){
        objProdutoModel.setA03_codigo(iCodigo);
    }
}
