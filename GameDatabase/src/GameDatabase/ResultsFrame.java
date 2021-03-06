/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameDatabase;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chris
 */
public class ResultsFrame extends javax.swing.JFrame {

    SQLController controller;
    
    /**
     * Creates new form ResultsFrame
     * @param table
     */
    public ResultsFrame(SQLController controller, DefaultTableModel table) {
        initComponents();
        this.controller = controller;
        ResultsTablePanel.setModel(table);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ResultsScrollPanel = new javax.swing.JScrollPane();
        ResultsTablePanel = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ResultsTablePanel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ResultsTablePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResultsTablePanelMouseClicked(evt);
            }
        });
        ResultsScrollPanel.setViewportView(ResultsTablePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ResultsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ResultsScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ResultsTablePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ResultsTablePanelMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = ResultsTablePanel.getSelectedRow();
            String title = ResultsTablePanel.getValueAt(row, 0).toString();
            String platform = ResultsTablePanel.getValueAt(row, 1).toString();
            Record record = controller.getRecord(title, platform);
            new IndividualResultFrame(controller, record, this).setVisible(true);
        }
    }//GEN-LAST:event_ResultsTablePanelMouseClicked

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ResultsScrollPanel;
    private javax.swing.JTable ResultsTablePanel;
    // End of variables declaration//GEN-END:variables
}