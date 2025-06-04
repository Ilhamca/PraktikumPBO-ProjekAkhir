/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

/**
 *
 * @author Iam
 */
public class ProjekAkhir {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Run GUI on the Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> {
            new View.LoginForm().setVisible(true);
        });
    }

}
