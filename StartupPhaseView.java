/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controllers.StartupPhaseController;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 * This class contains the View of the StartUp Phase
 * @author daksh
 */
public class StartupPhaseView extends javax.swing.JFrame {

    /**
     * ID of Current Player playing in the phase
     */
    int currentPlayer;
    /**
     * StartUp Phase Controller {@link controllers.StartupPhaseController}
     */
    StartupPhaseController spc;

    /**
     * Starts the View of StartUp Phase
     * @param spc {@link #spc}
     */
    public StartupPhaseView(StartupPhaseController spc) {
        this.spc = spc;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name = new javax.swing.JLabel();
        dropDown = new javax.swing.JComboBox();
        ok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        name.setText("jLabel1");

        dropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(name)
                .addGap(18, 18, 18)
                .addComponent(dropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ok)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed

        String countryName = dropDown.getSelectedItem().toString();
        spc.putArmy(currentPlayer, countryName);
        spc.updateView(currentPlayer);

    }//GEN-LAST:event_okActionPerformed

    /**
     * Shows the View (current player specific)
     * @param currentPlayer ID of the current player {@link #currentPlayer}
     */
    public void showView(int currentPlayer) {
        this.currentPlayer = currentPlayer;
        repaint(currentPlayer);
        this.setVisible(true);
    }

    /**
     * This method refreshes the View for the current player
     * @param currentPlayer ID of the current player {@link #currentPlayer}
     */
    public void repaint(int currentPlayer) {
        this.currentPlayer = currentPlayer;
        List<String> ls = spc.getList(currentPlayer);
        String playerName = spc.getName(currentPlayer);
        name.setText(playerName);
        dropDown.setModel(new DefaultComboBoxModel(ls.toArray()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox dropDown;
    private javax.swing.JLabel name;
    private javax.swing.JButton ok;
    // End of variables declaration//GEN-END:variables
}
