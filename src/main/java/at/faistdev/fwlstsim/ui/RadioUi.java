/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package at.faistdev.fwlstsim.ui;

import at.faistdev.fwlstsim.bl.game.GameProperties;
import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.bl.util.StringUtil;
import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.RadioMessage;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.ui.components.RadioRequestPanel;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ben
 */
public class RadioUi extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RadioUi.class.getName());

    private Vehicle currentConversation = null;

    /**
     * Creates new form RadioUi
     */
    public RadioUi() {
        initComponents();
        hideComponents();
        startRefreshThreads();
    }

    private void hideComponents() {
        demoRadioRequestPanel.setVisible(false);
        demoLeftPanel.setVisible(false);
        demoRightPanel.setVisible(false);
    }

    private void startRefreshThreads() {
        new Thread(() -> {
            while (true) {
                fillRadioRequests();
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException ex) {
                    System.getLogger(RadioUi.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        }, "RadioUi-FillRadioRequests").start();

        new Thread(() -> {
            while (true) {
                vehicleAnswersCall();
                try {
                    Thread.sleep(15_000);
                } catch (InterruptedException ex) {
                    System.getLogger(RadioUi.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        }, "RadioUi-VehicleAnswersCall").start();
    }

    private void vehicleAnswersCall() {
        if (currentConversation == null) {
            return;
        }

        if (responseEndMessageButton.isEnabled()) {
            return;
        }

        RadioMessage nextRadioMessage = currentConversation.getNextRadioMessage();
        addLeftRadioMessage(nextRadioMessage.getMessage());
        responseEndMessageButton.setEnabled(true);
    }

    public void fillRadioRequests() {
        requestPanel.removeAll();

        List<Vehicle> vehicles = OperationService.getVehiclesWithPendingRadioMessage();
        for (Vehicle vehicle : vehicles) {
            RadioRequestPanel panel = createRadioRequestPanel(vehicle);
            requestPanel.add(panel);
        }

        requestPanel.revalidate();
        requestPanel.repaint();
    }

    private RadioRequestPanel createRadioRequestPanel(Vehicle vehicle) {
        RadioRequestPanel panel = new RadioRequestPanel(vehicle);
        JLabel radioRequestLabel = new JLabel();
        javax.swing.Box.Filler filler = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        JLabel vehicleLabel = new JLabel();

        panel.setAlignmentX(0.0F);
        panel.setMinimumSize(new java.awt.Dimension(214, 23));
        panel.setPreferredSize(new java.awt.Dimension(214, 23));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.X_AXIS));

        radioRequestLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        radioRequestLabel.setText("Sprechwunsch:");
        radioRequestLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        radioRequestLabel.setMaximumSize(new java.awt.Dimension(100, 23));
        panel.add(radioRequestLabel);
        panel.add(filler);

        vehicleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        vehicleLabel.setText(vehicle.getName());
        vehicleLabel.setMaximumSize(new java.awt.Dimension(400, 16));
        panel.add(vehicleLabel);

        return panel;
    }

    private void onCallVehicle() {
        if (currentConversation != null) {
            return;
        }

        String vehicleName = callVehicleField.getText();
        if (StringUtil.isNullOrEmpty(vehicleName)) {
            return;
        }

        Vehicle vehicle = VehicleCache.getCache().getByName(vehicleName);
        if (vehicle == null) {
            return;
        }

        if (vehicle.getNextRadioMessage() == null) {
            return;
        }

        currentConversation = vehicle;
        callButton.setEnabled(false);
        callVehicleField.setText("");
        callVehicleField.setEnabled(false);

        addRightRadioMessage(getCallVehicleMessage(vehicle));
    }

    private String getCallVehicleMessage(Vehicle vehicle) {
        return vehicle.getName() + " von " + GameProperties.NAME_OF_DISPATCH + " kommen";
    }

    private void addRightRadioMessage(String text) {
        JPanel panel = new javax.swing.JPanel();
        javax.swing.Box.Filler filler = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        JLabel label = new javax.swing.JLabel();

        panel.setMaximumSize(new java.awt.Dimension(32767, 60));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.X_AXIS));
        panel.add(filler);

        label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label.setText("<html>" + text + "</html>");
        label.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        label.setMaximumSize(new java.awt.Dimension(300, 60));
        panel.add(label);

        messagesPanel.add(panel);
        messagesPanel.revalidate();
        messagesPanel.repaint();
    }

    private void addLeftRadioMessage(String text) {
        JPanel panel = new javax.swing.JPanel();
        JLabel label = new javax.swing.JLabel();

        panel.setMaximumSize(new java.awt.Dimension(32767, 60));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.X_AXIS));

        label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label.setText("<html>" + text + "</html>");
        label.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        label.setMaximumSize(new java.awt.Dimension(300, 60));
        panel.add(label);

        messagesPanel.add(panel);
        messagesPanel.revalidate();
        messagesPanel.repaint();
    }

    private void onEndCall() {
        if (currentConversation == null) {
            return;
        }

        addRightRadioMessage("Hier " + GameProperties.NAME_OF_DISPATCH + ", verstanden, Ende");
        currentConversation.removeNextRadioMessage();
        currentConversation = null;
        responseEndMessageButton.setEnabled(false);
        callButton.setEnabled(true);
        callVehicleField.setEnabled(true);
        fillRadioRequests();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        requestScrollPanel = new javax.swing.JScrollPane();
        requestPanel = new javax.swing.JPanel();
        demoRadioRequestPanel = new javax.swing.JPanel();
        demoRadioRequestLabel = new javax.swing.JLabel();
        demoRadioRequestFiller = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        demoRadioRequestVehicleLabel = new javax.swing.JLabel();
        messagesScrollPanel = new javax.swing.JScrollPane();
        messagesPanel = new javax.swing.JPanel();
        demoRightPanel = new javax.swing.JPanel();
        demoRightFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        demoRightLabel = new javax.swing.JLabel();
        demoLeftPanel = new javax.swing.JPanel();
        demoLeftLabel = new javax.swing.JLabel();
        actionPanel = new javax.swing.JPanel();
        callVehiclePanel = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        callVehicleField = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        callButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        responsePanel = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        responseEndMessageButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Funk");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        requestScrollPanel.setAlignmentX(0.0F);
        requestScrollPanel.setMaximumSize(new java.awt.Dimension(32767, 100));
        requestScrollPanel.setMinimumSize(new java.awt.Dimension(16, 100));
        requestScrollPanel.setPreferredSize(new java.awt.Dimension(216, 100));

        requestPanel.setMaximumSize(new java.awt.Dimension(510, 100));
        requestPanel.setMinimumSize(new java.awt.Dimension(214, 100));
        requestPanel.setLayout(new javax.swing.BoxLayout(requestPanel, javax.swing.BoxLayout.Y_AXIS));

        demoRadioRequestPanel.setAlignmentX(0.0F);
        demoRadioRequestPanel.setMinimumSize(new java.awt.Dimension(214, 23));
        demoRadioRequestPanel.setPreferredSize(new java.awt.Dimension(214, 23));
        demoRadioRequestPanel.setLayout(new javax.swing.BoxLayout(demoRadioRequestPanel, javax.swing.BoxLayout.X_AXIS));

        demoRadioRequestLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        demoRadioRequestLabel.setText("Sprechwunsch:");
        demoRadioRequestLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        demoRadioRequestLabel.setMaximumSize(new java.awt.Dimension(100, 23));
        demoRadioRequestPanel.add(demoRadioRequestLabel);
        demoRadioRequestPanel.add(demoRadioRequestFiller);

        demoRadioRequestVehicleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        demoRadioRequestVehicleLabel.setText("TLF 1000 Blumegg Teipl");
        demoRadioRequestVehicleLabel.setMaximumSize(new java.awt.Dimension(400, 16));
        demoRadioRequestPanel.add(demoRadioRequestVehicleLabel);

        requestPanel.add(demoRadioRequestPanel);

        requestScrollPanel.setViewportView(requestPanel);

        getContentPane().add(requestScrollPanel);

        messagesScrollPanel.setAlignmentX(0.0F);
        messagesScrollPanel.setMinimumSize(new java.awt.Dimension(16, 200));
        messagesScrollPanel.setPreferredSize(new java.awt.Dimension(411, 200));

        messagesPanel.setMaximumSize(new java.awt.Dimension(32767, 32767));
        messagesPanel.setMinimumSize(new java.awt.Dimension(307, 200));
        messagesPanel.setPreferredSize(new java.awt.Dimension(409, 200));
        messagesPanel.setLayout(new javax.swing.BoxLayout(messagesPanel, javax.swing.BoxLayout.Y_AXIS));

        demoRightPanel.setMaximumSize(new java.awt.Dimension(32767, 60));
        demoRightPanel.setLayout(new javax.swing.BoxLayout(demoRightPanel, javax.swing.BoxLayout.X_AXIS));
        demoRightPanel.add(demoRightFiller);

        demoRightLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        demoRightLabel.setText("TLF 1000 Blumegg Teipl von Florian Steiermark kommen");
        demoRightLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        demoRightLabel.setMaximumSize(new java.awt.Dimension(300, 60));
        demoRightPanel.add(demoRightLabel);

        messagesPanel.add(demoRightPanel);

        demoLeftPanel.setMaximumSize(new java.awt.Dimension(32767, 60));
        demoLeftPanel.setLayout(new javax.swing.BoxLayout(demoLeftPanel, javax.swing.BoxLayout.X_AXIS));

        demoLeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        demoLeftLabel.setText("<html>Hier TLF 1000 Blumegg Teipl, PKW in Graben, keine weiteren Kräfte benötigt</html>");
        demoLeftLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        demoLeftLabel.setMaximumSize(new java.awt.Dimension(300, 60));
        demoLeftPanel.add(demoLeftLabel);

        messagesPanel.add(demoLeftPanel);

        messagesScrollPanel.setViewportView(messagesPanel);

        getContentPane().add(messagesScrollPanel);

        actionPanel.setMaximumSize(new java.awt.Dimension(32767, 66));
        actionPanel.setPreferredSize(new java.awt.Dimension(647, 66));
        actionPanel.setLayout(new javax.swing.BoxLayout(actionPanel, javax.swing.BoxLayout.Y_AXIS));

        callVehiclePanel.setAlignmentX(0.0F);
        callVehiclePanel.setMaximumSize(new java.awt.Dimension(2147483647, 33));
        callVehiclePanel.setLayout(new javax.swing.BoxLayout(callVehiclePanel, javax.swing.BoxLayout.X_AXIS));
        callVehiclePanel.add(filler3);

        callVehicleField.setAlignmentX(0.0F);
        callVehicleField.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        callVehiclePanel.add(callVehicleField);
        callVehiclePanel.add(filler1);

        callButton.setText("Anfunken");
        callButton.addActionListener(this::callButtonActionPerformed);
        callVehiclePanel.add(callButton);
        callVehiclePanel.add(filler2);

        actionPanel.add(callVehiclePanel);

        responsePanel.setAlignmentX(0.0F);
        responsePanel.setLayout(new javax.swing.BoxLayout(responsePanel, javax.swing.BoxLayout.X_AXIS));
        responsePanel.add(filler4);

        responseEndMessageButton.setText("Verstanden, Ende");
        responseEndMessageButton.setEnabled(false);
        responseEndMessageButton.addActionListener(this::responseEndMessageButtonActionPerformed);
        responsePanel.add(responseEndMessageButton);

        actionPanel.add(responsePanel);

        getContentPane().add(actionPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void callButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callButtonActionPerformed
        onCallVehicle();
    }//GEN-LAST:event_callButtonActionPerformed

    private void responseEndMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_responseEndMessageButtonActionPerformed
        onEndCall();
    }//GEN-LAST:event_responseEndMessageButtonActionPerformed

    public static void create() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            RadioUi ui = new RadioUi();
            ui.setVisible(true);

            UiRegistry.add(ui);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton callButton;
    private javax.swing.JTextField callVehicleField;
    private javax.swing.JPanel callVehiclePanel;
    private javax.swing.JLabel demoLeftLabel;
    private javax.swing.JPanel demoLeftPanel;
    private javax.swing.Box.Filler demoRadioRequestFiller;
    private javax.swing.JLabel demoRadioRequestLabel;
    private javax.swing.JPanel demoRadioRequestPanel;
    private javax.swing.JLabel demoRadioRequestVehicleLabel;
    private javax.swing.Box.Filler demoRightFiller;
    private javax.swing.JLabel demoRightLabel;
    private javax.swing.JPanel demoRightPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JPanel messagesPanel;
    private javax.swing.JScrollPane messagesScrollPanel;
    private javax.swing.JPanel requestPanel;
    private javax.swing.JScrollPane requestScrollPanel;
    private javax.swing.JButton responseEndMessageButton;
    private javax.swing.JPanel responsePanel;
    // End of variables declaration//GEN-END:variables
}
