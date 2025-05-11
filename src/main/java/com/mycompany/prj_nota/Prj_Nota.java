/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.prj_nota;
import com.mycompany.prj_nota.Pck_View.ClienteView;

import javax.swing.*;

/**
 *
 * @author lab03aluno
 */
public class Prj_Nota {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        null,
                        (e.getMessage() != null) ? e.getMessage() : e.toString(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            });
        });

        SwingUtilities.invokeLater(() -> {
            ClienteView cv = new ClienteView();
            cv.setVisible(true);
        });
    }

}
