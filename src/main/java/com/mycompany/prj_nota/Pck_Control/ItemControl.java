package com.mycompany.prj_nota.Pck_Control;

import com.mycompany.prj_nota.Pck_Dao.ConexaoMySql;
import com.mycompany.prj_nota.Pck_Model.ItemModel;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemControl {

    public void inserirItem(int iCodProduto, int iCodPedido, int iQuantidade, double dValorItem) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection()) {
            conn.setAutoCommit(false);

            // Obter produto e verificar estoque
            ProdutoControl objProdutoControl = new ProdutoControl();
            ProdutoModel objProdutoModel = objProdutoControl.consultarProduto(iCodProduto);
            int quantidadeRestante = objProdutoModel.getA03_estoque() - iQuantidade;
            if (quantidadeRestante < 0) {
                throw new IllegalArgumentException("Sem estoque o suficiente");
            }

            int codCliente = 0;
            double valorPedidoAtual = 0.0;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT A01_codigo, A02_valorTotal FROM PEDIDO_02 WHERE A02_codigo = ?")) {
                stmt.setInt(1, iCodPedido);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        codCliente = rs.getInt("A01_codigo");
                        valorPedidoAtual = rs.getDouble("A02_valorTotal");
                    } else {
                        throw new IllegalArgumentException("Pedido não encontrado.");
                    }
                }
            }

            double creditoDisponivel = 0.0;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT A01_credito FROM CLIENTE_01 WHERE A01_codigo = ? AND A01_ativo = TRUE")) {
                stmt.setInt(1, codCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        creditoDisponivel = rs.getDouble("A01_credito");
                    } else {
                        throw new IllegalArgumentException("Cliente inativo ou não encontrado.");
                    }
                }
            }

            double novoTotal = valorPedidoAtual + dValorItem;
            if (novoTotal > creditoDisponivel) {
                throw new IllegalArgumentException("Crédito insuficiente");
            }

            // Atualizar crédito do cliente
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE CLIENTE_01 SET A01_credito = ? WHERE A01_codigo = ?")) {
                stmt.setDouble(1, creditoDisponivel - dValorItem);
                stmt.setInt(2, codCliente);
                stmt.executeUpdate();
            }

            try (CallableStatement stmt1 = conn.prepareCall("{CALL Proc_InsItem(?, ?, ?, ?)}")) {
                stmt1.setInt(1, iCodProduto);
                stmt1.setInt(2, iCodPedido);
                stmt1.setInt(3, iQuantidade);
                stmt1.setDouble(4, dValorItem);
                stmt1.execute();
            }

            try (CallableStatement stmt3 = conn.prepareCall("{CALL Proc_UpdProduto(?, ?, ?, ?)}")) {
                stmt3.setInt(1, objProdutoModel.getA03_codigo());
                stmt3.setString(2, objProdutoModel.getA03_descricao());
                stmt3.setDouble(3, objProdutoModel.getA03_valorUnitario());
                stmt3.setInt(4, quantidadeRestante);
                stmt3.execute();
            }

            try (CallableStatement stmt2 = conn.prepareCall("{CALL Proc_UpdPedidoValorTotal(?)}")) {
                stmt2.setInt(1, iCodPedido);
                stmt2.execute();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                System.out.println("Erro, desfazendo transação: " + e.getMessage());
                conexao.getConnection().rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao fazer rollback: " + rollbackEx.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar item: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }



    public void atualizarItem(int iCodItem, int iCodPedido, int iQuantidade, double dValorItem) {
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdItem(?, ?, ?)}")) {

            stmt.setInt(1, iCodItem);
            stmt.setInt(2, iQuantidade);
            stmt.setDouble(3, dValorItem);
            stmt.execute();
            try (CallableStatement stmt2 = conn.prepareCall("{CALL Proc_UpdPedidoValorTotal(?)}")) {
                stmt2.setInt(1, iCodPedido);
                stmt2.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item: " + e.getMessage());
        }
    }

    public void deletarItem(int iCodItem) {
        ConexaoMySql conexao = new ConexaoMySql();

        try (var conn = conexao.getConnection()) {
            conn.setAutoCommit(false);

            int codProduto = 0;
            int codPedido = 0;
            int quantidade = 0;
            double valorItem = 0.0;

            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT A03_codigo, A02_codigo, A04_quantidade, A04_valorItem FROM ITEM_04 WHERE A04_codigo = ?")) {
                stmt.setInt(1, iCodItem);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        codProduto = rs.getInt("A03_codigo");
                        codPedido = rs.getInt("A02_codigo");
                        quantidade = rs.getInt("A04_quantidade");
                        valorItem = rs.getDouble("A04_valorItem");
                    } else {
                        throw new IllegalArgumentException("Item não encontrado.");
                    }
                }
            }

            // Consultar cliente vinculado ao pedido
            int codCliente = 0;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT A01_codigo FROM PEDIDO_02 WHERE A02_codigo = ?")) {
                stmt.setInt(1, codPedido);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        codCliente = rs.getInt("A01_codigo");
                    } else {
                        throw new IllegalArgumentException("Pedido não encontrado.");
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE CLIENTE_01 SET A01_credito = A01_credito + ? WHERE A01_codigo = ?")) {
                stmt.setDouble(1, valorItem);
                stmt.setInt(2, codCliente);
                stmt.executeUpdate();
            }

            // Restaurar estoque do produto
            ProdutoControl objProdutoControl = new ProdutoControl();
            ProdutoModel objProduto = objProdutoControl.consultarProduto(codProduto);
            int novoEstoque = objProduto.getA03_estoque() + quantidade;

            try (CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdProduto(?, ?, ?, ?)}")) {
                stmt.setInt(1, objProduto.getA03_codigo());
                stmt.setString(2, objProduto.getA03_descricao());
                stmt.setDouble(3, objProduto.getA03_valorUnitario());
                stmt.setInt(4, novoEstoque);
                stmt.execute();
            }

            try (CallableStatement stmt = conn.prepareCall("{CALL Proc_DelItem(?)}")) {
                stmt.setInt(1, iCodItem);
                stmt.execute();
            }

            try (CallableStatement stmt = conn.prepareCall("{CALL Proc_UpdPedidoValorTotal(?)}")) {
                stmt.setInt(1, codPedido);
                stmt.execute();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                System.out.println("Erro, desfazendo transação: " + e.getMessage());
                conexao.getConnection().rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao fazer rollback: " + rollbackEx.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao deletar item: " + e.getMessage());
        }
    }


    public ItemModel consultarItem(int iCodItem) {
        ItemModel item = new ItemModel();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04 WHERE A04_codigo = ?")) {

            stmt.setInt(1, iCodItem);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item.setA04_codigo(rs.getInt("A04_codigo"));
                    item.setA03_codigo(rs.getInt("A03_codigo"));
                    item.setA02_codigo(rs.getInt("A02_codigo"));
                    item.setA04_quantidade(rs.getInt("A04_quantidade"));
                    item.setA04_valorItem(rs.getDouble("A04_valorItem"));
                    return item;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar item: " + e.getMessage());
        }
        return null;
    }

    public List<ItemModel> consultarItens() {
        List<ItemModel> itens = new ArrayList<>();
        ConexaoMySql conexao = new ConexaoMySql();
        try (var conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ITEM_04");
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemModel item = new ItemModel();
                item.setA04_codigo(rs.getInt("A04_codigo"));
                item.setA03_codigo(rs.getInt("A03_codigo"));
                item.setA02_codigo(rs.getInt("A02_codigo"));
                item.setA04_quantidade(rs.getInt("A04_quantidade"));
                item.setA04_valorItem(rs.getDouble("A04_valorItem"));
                itens.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar itens: " + e.getMessage());
        }
        return itens;
    }
}
