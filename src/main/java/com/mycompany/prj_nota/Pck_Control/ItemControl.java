package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Model.ItemModel;

public class ItemControl {
    ItemModel objItemModel = new ItemModel();

    public void inserirItem(int iCodigo, int iProdutoCodigo, int iPedidoCodigo, int iQuantidade, double dValorTotal) {
        objItemModel.setA04_codigo(iCodigo);
        objItemModel.setA03_codigo(iProdutoCodigo);
        objItemModel.setA02_codigo(iPedidoCodigo);
        objItemModel.setA04_quantidade(iQuantidade);
        objItemModel.setA04_valorItem(dValorTotal);
    }

    public void excluirItem(int iCodigo) {
        objItemModel.setA04_codigo(iCodigo);
    }

    public void atualizarItem(int iCodigo, int iProdutoCodigo, int iPedidoCodigo, int iQuantidade, double dValorTotal) {
        objItemModel.setA04_codigo(iCodigo);
        objItemModel.setA03_codigo(iProdutoCodigo);
        objItemModel.setA02_codigo(iPedidoCodigo);
        objItemModel.setA04_quantidade(iQuantidade);
        objItemModel.setA04_valorItem(dValorTotal);
    }

    public void consultarItem(int iCodigo) {
        objItemModel.setA04_codigo(iCodigo);
    }
}
