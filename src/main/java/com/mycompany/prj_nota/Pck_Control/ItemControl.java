package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ItemModel;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;
import com.mycompany.prj_nota.Pck_Persistencia.ItemPersistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemControl {

    public void inserirItem(int iCodProduto, int iCodPedido, int iQuantidade, double dValorItem) {
        ItemModel objItemModel = new ItemModel();
        objItemModel.setA03_codigo(iCodProduto);
        objItemModel.setA02_codigo(iCodPedido);
        objItemModel.setA04_quantidade(iQuantidade);
        objItemModel.setA04_valorItem(dValorItem);
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        objItemPersistencia.inserirItemPersistencia(objItemModel);
    }



    public void atualizarItem(int iCodItem, int iQuantidade, double dValorItem) {
        ItemModel objItemModel = new ItemModel();
        objItemModel.setA04_codigo(iCodItem);
        objItemModel.setA04_quantidade(iQuantidade);
        objItemModel.setA04_valorItem(dValorItem);
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        objItemPersistencia.atualizarItemPersistencia(objItemModel);
    }

    public void deletarItem(int iCodItem) {
        ItemModel objItemModel = new ItemModel();
        objItemModel.setA04_codigo(iCodItem);
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        objItemPersistencia.deletarItemPersistencia(objItemModel);
    }



    public ItemModel consultarItem(int iCodItem) {
        ItemModel objItemModel = new ItemModel();
        objItemModel.setA04_codigo(iCodItem);
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        return objItemPersistencia.consultarItensPersistencia(objItemModel);
    }

    public List<ItemModel> consultarItens() {
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        return objItemPersistencia.consultarItensPersistencia();
    }
}