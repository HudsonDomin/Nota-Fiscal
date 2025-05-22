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
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        objItemPersistencia.inserirItemPersistencia(iCodProduto, iCodPedido, iQuantidade, dValorItem);
        //(int iCodProduto, int iCodPedido, int iQuantidade, double dValorItem)
    }



    public void atualizarItem(int iCodItem, int iQuantidade, double dValorItem) {
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        objItemPersistencia.atualizarItemPersistencia(iCodItem, iQuantidade, dValorItem);
    }

    public void deletarItem(int iCodItem) {
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        objItemPersistencia.deletarItemPersistencia(iCodItem);
    }



    public ItemModel consultarItem(int iCodItem) {
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        objItemPersistencia.consultarItensPersistencia(iCodItem);
        return null;
    }

    public List<ItemModel> consultarItens() {
        ItemPersistencia objItemPersistencia = new ItemPersistencia();
        return objItemPersistencia.consultarItensPersistencia();
    }
}
