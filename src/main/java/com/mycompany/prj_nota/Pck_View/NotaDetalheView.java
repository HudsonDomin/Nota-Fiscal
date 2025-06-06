/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.prj_nota.Pck_View;

import com.mycompany.prj_nota.Pck_Model.ClienteModel;
import com.mycompany.prj_nota.Pck_Model.ItemModel;
import com.mycompany.prj_nota.Pck_Model.PedidoModel;
import com.mycompany.prj_nota.Pck_Model.ProdutoModel;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author caiomtho
 */
public class NotaDetalheView extends javax.swing.JDialog {

    /**
     * Creates new form PedidoDetalheView
     */
    private ClienteModel objClienteModel;
    private PedidoModel objPedidoModel;
    private List<ProdutoModel> listaProdutoModel;
    private List<ItemModel> listaItemModel;
    public NotaDetalheView(
            java.awt.Frame parent, 
            boolean modal, 
            ClienteModel oClienteModel,
            PedidoModel oPedidoModel,
            List<ProdutoModel> lProdutoModel,
            List<ItemModel> lItemModel) {
       super(parent, modal);
       initComponents();
       objClienteModel = oClienteModel;
       objPedidoModel = oPedidoModel;
       listaProdutoModel = lProdutoModel;
       listaItemModel = lItemModel;
       
       tituloPedidoLabel.setText(String.format(
               "%d-%s",
               objPedidoModel.getA02_codigo(), 
               objPedidoModel.getA02_data().toString())
       );
       codClienteText.setText(String.valueOf(objClienteModel.getA01_codigo()));
       cpfText.setText(String.valueOf(objClienteModel.getA01_cpf()));
       nomeClienteText.setText(String.valueOf(objClienteModel.getA01_nome()));
       valorTotalText.setText(String.format("%.2f", objPedidoModel.getA02_valorTotal()));
       atualizarTabela();
    }
    public NotaDetalheView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void atualizarTabela(){
       DefaultTableModel objTableModel = (DefaultTableModel) tabelaDetalhe.getModel();
       objTableModel.setRowCount(0);
       for (ItemModel objItemModel : listaItemModel ) {
           objTableModel.addRow(new Object[]{
                   objItemModel.getA04_codigo(),
                   listaProdutoModel.stream()
                           .filter(p -> p.getA03_codigo() == objItemModel.getA03_codigo())
                           .map(ProdutoModel::getA03_descricao)
                           .findFirst()
                           .orElseThrow(() -> new RuntimeException("Produto não encontrado para o código " + objItemModel.getA03_codigo())),
                   objItemModel.getA04_quantidade(),
                   objItemModel.getA04_valorItem(),
           });
       }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        tituloPedidoLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaDetalhe = new javax.swing.JTable();
        codClienteText = new javax.swing.JTextField();
        nomeClienteText = new javax.swing.JTextField();
        cpfText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        valorTotalText = new javax.swing.JTextField();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tituloPedidoLabel.setText("NúmeroDoPedido-DataDoPedido");
        tituloPedidoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel1.setText("Código Cliente:");

        jLabel2.setText("Nome do Cliente:");

        jLabel3.setText("CPF:");

        tabelaDetalhe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código Item", "Descrição", "Quantidade", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDetalhe.setMaximumSize(new java.awt.Dimension(52525235, 80));
        tabelaDetalhe.setMinimumSize(new java.awt.Dimension(60, 40));
        tabelaDetalhe.setOpaque(false);
        jScrollPane1.setViewportView(tabelaDetalhe);
        if (tabelaDetalhe.getColumnModel().getColumnCount() > 0) {
            tabelaDetalhe.getColumnModel().getColumn(0).setResizable(false);
            tabelaDetalhe.getColumnModel().getColumn(1).setResizable(false);
            tabelaDetalhe.getColumnModel().getColumn(2).setResizable(false);
            tabelaDetalhe.getColumnModel().getColumn(3).setResizable(false);
        }

        codClienteText.setEditable(false);
        codClienteText.setPreferredSize(new java.awt.Dimension(200, 25));

        nomeClienteText.setEditable(false);

        cpfText.setEditable(false);

        jLabel5.setText("Total");

        valorTotalText.setEditable(false);
        valorTotalText.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(tituloPedidoLabel)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cpfText)
                            .addComponent(nomeClienteText)
                            .addComponent(codClienteText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(valorTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(61, 61, 61))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tituloPedidoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(codClienteText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nomeClienteText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(valorTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cpfText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NotaDetalheView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotaDetalheView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotaDetalheView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotaDetalheView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NotaDetalheView dialog = new NotaDetalheView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codClienteText;
    private javax.swing.JTextField cpfText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nomeClienteText;
    private javax.swing.JTable tabelaDetalhe;
    private javax.swing.JLabel tituloPedidoLabel;
    private javax.swing.JTextField valorTotalText;
    // End of variables declaration//GEN-END:variables
}
