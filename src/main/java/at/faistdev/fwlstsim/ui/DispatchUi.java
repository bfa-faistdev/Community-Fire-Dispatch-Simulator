/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package at.faistdev.fwlstsim.ui;

import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleStatus;
import at.faistdev.fwlstsim.ui.components.VehiclePanel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Ben
 */
public class DispatchUi extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DispatchUi.class.getName());

    private Operation selectedOperation;

    /**
     * Creates new form DispatchUi
     */
    public DispatchUi() {
        initComponents();
        selectedOperation = null;
    }

    public void setSelectedOperation(Operation operation) {
        selectedOperation = operation;
        System.out.println("Selected operation: " + selectedOperation.getCallingNumber());

        boolean isOperation = operation != null;
        selectVehiclesButton.setEnabled(isOperation);

        loadAllDispatchedVehicles();
        fillData();
        enableDispatchButton();
    }

    private void fillData() {
        callerNumberField.setText("");
        addressField.setText("");

        if (selectedOperation == null) {
            return;
        }

        callerNumberField.setText(selectedOperation.getCallingNumber());
        addressField.setText(selectedOperation.getLocation().getText());
    }

    private void onSelectVehiclesBtnClick() {
        selectVehiclesDialog.setVisible(true);
        loadAllVehiclesIntoSelectVehiclesDialog();
    }

    private void loadAllVehiclesIntoSelectVehiclesDialog() {
        innerSelectVehiclesScrollPanel.removeAll();

        ArrayList<Vehicle> allVehicles = VehicleCache.getCache().getAll();
        for (Vehicle vehicle : allVehicles) {
            VehiclePanel panel = createSelectVehiclePanel(vehicle);
            innerSelectVehiclesScrollPanel.add(panel);
        }

        innerSelectVehiclesScrollPanel.revalidate();
        innerSelectVehiclesScrollPanel.repaint();
    }

    private void loadAllDispatchedVehicles() {
        innerDispatchedVehiclesScrollPanel.removeAll();

        if (selectedOperation == null) {
            return;
        }

        Set<Vehicle> dispatchedVehicles = selectedOperation.getVehicles();
        for (Vehicle vehicle : dispatchedVehicles) {
            addVehicleToDispatchedPanel(vehicle);
        }
    }

    private void addVehicleToDispatchedPanel(Vehicle vehicle) {
        if (isAlreadyInDispatchedPanel(vehicle)) {
            return;
        }

        VehiclePanel panel = createDispatchedVehiclePanel(vehicle);
        innerDispatchedVehiclesScrollPanel.add(panel);

        innerDispatchedVehiclesScrollPanel.revalidate();
        innerDispatchedVehiclesScrollPanel.repaint();

        enableDispatchButton();
    }

    private boolean isAlreadyInDispatchedPanel(Vehicle vehicle) {
        Component[] components = innerDispatchedVehiclesScrollPanel.getComponents();
        for (Component component : components) {
            VehiclePanel panel = (VehiclePanel) component;
            if (panel.getVehicle().equals(vehicle)) {
                return true;
            }
        }

        return false;
    }

    private VehiclePanel createSelectVehiclePanel(Vehicle vehicle) {
        VehiclePanel panel = new VehiclePanel(vehicle);
        JTextField statusField = new JTextField();
        JLabel vehicleLabel = new JLabel();
        JCheckBox checkBox = new JCheckBox();

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setMaximumSize(new java.awt.Dimension(32767, 23));
        panel.setLayout(new java.awt.GridLayout(1, 3));

        statusField.setEnabled(false);
        statusField.setBackground(VehicleStatusUtil.getColor(vehicle.getStatus()));
        statusField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        statusField.setText(vehicle.getStatus().getText());
        panel.add(statusField);

        vehicleLabel.setBackground(new java.awt.Color(255, 255, 255));
        vehicleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vehicleLabel.setText(vehicle.getName());
        vehicleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        panel.add(vehicleLabel);

        checkBox.setBackground(new java.awt.Color(255, 255, 255));
        checkBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        checkBox.setEnabled(isVehicleAllowedToBeDispatched(vehicle));
        panel.add(checkBox);

        return panel;
    }

    private boolean isVehicleAllowedToBeDispatched(Vehicle vehicle) {
        return vehicle.getStatus() == VehicleStatus.STATUS_9 && OperationService.isVehicleDispatched(vehicle) == false;
    }

    private VehiclePanel createDispatchedVehiclePanel(Vehicle vehicle) {
        VehiclePanel panel = new VehiclePanel(vehicle);
        JTextField statusField = new JTextField();
        JLabel vehicleLabel = new JLabel();

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setMaximumSize(new java.awt.Dimension(32767, 23));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.X_AXIS));

        statusField.setBackground(VehicleStatusUtil.getColor(vehicle.getStatus()));
        statusField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        statusField.setText(vehicle.getStatus().getText());
        statusField.setEnabled(false);
        statusField.setMaximumSize(new java.awt.Dimension(50, 2147483647));
        panel.add(statusField);

        vehicleLabel.setBackground(new java.awt.Color(255, 255, 255));
        vehicleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        vehicleLabel.setText(vehicle.getName());
        vehicleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        vehicleLabel.setMaximumSize(new java.awt.Dimension(1000, 22));
        vehicleLabel.setMinimumSize(new java.awt.Dimension(250, 22));
        vehicleLabel.setPreferredSize(new java.awt.Dimension(250, 22));
        panel.add(vehicleLabel);

        return panel;
    }

    private void onSelectVehiclesSave() {
        Set<Vehicle> selectedVehicles = getSelectedVehiclesFromDialog();
        for (Vehicle vehicle : selectedVehicles) {
            addVehicleToDispatchedPanel(vehicle);
        }

        selectVehiclesDialog.setVisible(false);
    }

    private Set<Vehicle> getSelectedVehiclesFromDialog() {
        Set<Vehicle> selectedVehicles = new HashSet<>();
        Component[] vehiclePanels = innerSelectVehiclesScrollPanel.getComponents();
        for (Component panel : vehiclePanels) {
            VehiclePanel selectVehiclePanel = (VehiclePanel) panel;

            JCheckBox checkBox = getCheckBox(selectVehiclePanel.getComponents());
            if (!checkBox.isSelected()) {
                continue;
            }

            Vehicle vehicle = selectVehiclePanel.getVehicle();
            if (isVehicleAllowedToBeDispatched(vehicle) == false) {
                continue;
            }

            selectedVehicles.add(vehicle);
        }

        return selectedVehicles;
    }

    private Set<Vehicle> getDispatchedVehiclesFromDialog() {
        Set<Vehicle> vehicles = new HashSet<>();
        Component[] vehiclePanels = innerDispatchedVehiclesScrollPanel.getComponents();
        for (Component panel : vehiclePanels) {
            VehiclePanel vehiclePanel = (VehiclePanel) panel;

            Vehicle vehicle = vehiclePanel.getVehicle();
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    private JCheckBox getCheckBox(Component[] components) {
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                return (JCheckBox) component;
            }
        }

        throw new IllegalStateException("No Checkbox found in components");
    }

    private void enableDispatchButton() {
        dispatchButton.setEnabled(selectedOperation != null && innerDispatchedVehiclesScrollPanel.getComponentCount() > 0);
    }

    private void onDispatch() {
        if (selectedOperation == null) {
            return;
        }

        Set<Vehicle> vehiclesToDispatch = getDispatchedVehiclesFromDialog();
        OperationService.dispatchVehicles(selectedOperation, vehiclesToDispatch);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectVehiclesDialog = new javax.swing.JDialog();
        toolbarPanel = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        saveVehiclesButton = new javax.swing.JButton();
        selectVehiclesScrollPanel = new javax.swing.JScrollPane();
        innerSelectVehiclesScrollPanel = new javax.swing.JPanel();
        toolPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        selectVehiclesButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(500, 0));
        dispatchButton = new javax.swing.JButton();
        lowerPanel = new javax.swing.JPanel();
        dataPanel = new javax.swing.JPanel();
        callerNumberPanel = new javax.swing.JPanel();
        callerNumberLabel = new javax.swing.JLabel();
        callerNumberField = new javax.swing.JTextField();
        addressPanel = new javax.swing.JPanel();
        adressLabel = new javax.swing.JLabel();
        addressField = new javax.swing.JTextField();
        keywordPanel = new javax.swing.JPanel();
        keywordLabel = new javax.swing.JLabel();
        keywordComboBox = new javax.swing.JComboBox<>();
        infoTextPanel = new javax.swing.JPanel();
        infoTextLabel = new javax.swing.JLabel();
        infoTextField = new javax.swing.JTextField();
        vehiclesPanel = new javax.swing.JPanel();
        dispatchedVehiclesScrollPanel = new javax.swing.JScrollPane();
        innerDispatchedVehiclesScrollPanel = new javax.swing.JPanel();

        selectVehiclesDialog.setTitle("Fahrzeug zuweisen");
        selectVehiclesDialog.setMinimumSize(new java.awt.Dimension(600, 300));
        selectVehiclesDialog.getContentPane().setLayout(new javax.swing.BoxLayout(selectVehiclesDialog.getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        toolbarPanel.setBackground(new java.awt.Color(255, 255, 255));
        toolbarPanel.setMaximumSize(new java.awt.Dimension(32767, 33));
        toolbarPanel.setMinimumSize(new java.awt.Dimension(0, 23));
        toolbarPanel.setPreferredSize(new java.awt.Dimension(374, 33));
        toolbarPanel.setLayout(new javax.swing.BoxLayout(toolbarPanel, javax.swing.BoxLayout.X_AXIS));
        toolbarPanel.add(filler2);

        saveVehiclesButton.setText("Speichern");
        saveVehiclesButton.addActionListener(this::saveVehiclesButtonActionPerformed);
        toolbarPanel.add(saveVehiclesButton);

        selectVehiclesDialog.getContentPane().add(toolbarPanel);

        selectVehiclesScrollPanel.setBackground(new java.awt.Color(255, 255, 255));

        innerSelectVehiclesScrollPanel.setBackground(new java.awt.Color(255, 255, 255));
        innerSelectVehiclesScrollPanel.setLayout(new javax.swing.BoxLayout(innerSelectVehiclesScrollPanel, javax.swing.BoxLayout.Y_AXIS));
        selectVehiclesScrollPanel.setViewportView(innerSelectVehiclesScrollPanel);

        selectVehiclesDialog.getContentPane().add(selectVehiclesScrollPanel);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Einsatzverwaltung");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(960, 540));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        toolPanel.setMaximumSize(new java.awt.Dimension(10000, 33));
        toolPanel.setPreferredSize(new java.awt.Dimension(400, 33));
        toolPanel.setLayout(new javax.swing.BoxLayout(toolPanel, javax.swing.BoxLayout.X_AXIS));
        toolPanel.add(filler1);

        selectVehiclesButton.setText("Fahrzeug wählen");
        selectVehiclesButton.setEnabled(false);
        selectVehiclesButton.addActionListener(this::selectVehiclesButtonActionPerformed);
        toolPanel.add(selectVehiclesButton);
        toolPanel.add(filler3);

        dispatchButton.setText("Alarmieren");
        dispatchButton.setEnabled(false);
        dispatchButton.addActionListener(this::dispatchButtonActionPerformed);
        toolPanel.add(dispatchButton);

        getContentPane().add(toolPanel);

        lowerPanel.setLayout(new java.awt.GridLayout(1, 2));

        dataPanel.setLayout(new javax.swing.BoxLayout(dataPanel, javax.swing.BoxLayout.Y_AXIS));

        callerNumberPanel.setMaximumSize(new java.awt.Dimension(10000, 50));
        callerNumberPanel.setLayout(new javax.swing.BoxLayout(callerNumberPanel, javax.swing.BoxLayout.Y_AXIS));

        callerNumberLabel.setLabelFor(addressField);
        callerNumberLabel.setText("Telefonnummer");
        callerNumberLabel.setMaximumSize(new java.awt.Dimension(1000, 16));
        callerNumberPanel.add(callerNumberLabel);

        callerNumberField.setAlignmentX(0.0F);
        callerNumberField.setEnabled(false);
        callerNumberPanel.add(callerNumberField);

        dataPanel.add(callerNumberPanel);

        addressPanel.setAlignmentX(0.0F);
        addressPanel.setMaximumSize(new java.awt.Dimension(10000, 50));
        addressPanel.setLayout(new javax.swing.BoxLayout(addressPanel, javax.swing.BoxLayout.Y_AXIS));

        adressLabel.setLabelFor(addressField);
        adressLabel.setText("Adresse");
        adressLabel.setMaximumSize(new java.awt.Dimension(1000, 16));
        addressPanel.add(adressLabel);

        addressField.setAlignmentX(0.0F);
        addressField.setEnabled(false);
        addressPanel.add(addressField);

        dataPanel.add(addressPanel);

        keywordPanel.setMaximumSize(new java.awt.Dimension(10000, 50));
        keywordPanel.setLayout(new javax.swing.BoxLayout(keywordPanel, javax.swing.BoxLayout.Y_AXIS));

        keywordLabel.setLabelFor(addressField);
        keywordLabel.setText("Einsatzstichwort");
        keywordLabel.setMaximumSize(new java.awt.Dimension(1000, 16));
        keywordPanel.add(keywordLabel);

        keywordComboBox.setAlignmentX(0.0F);
        keywordPanel.add(keywordComboBox);

        dataPanel.add(keywordPanel);

        infoTextPanel.setMaximumSize(new java.awt.Dimension(10000, 50));
        infoTextPanel.setLayout(new javax.swing.BoxLayout(infoTextPanel, javax.swing.BoxLayout.Y_AXIS));

        infoTextLabel.setLabelFor(addressField);
        infoTextLabel.setText("Info");
        infoTextLabel.setMaximumSize(new java.awt.Dimension(1000, 16));
        infoTextPanel.add(infoTextLabel);

        infoTextField.setAlignmentX(0.0F);
        infoTextPanel.add(infoTextField);

        dataPanel.add(infoTextPanel);

        lowerPanel.add(dataPanel);

        vehiclesPanel.setLayout(new javax.swing.BoxLayout(vehiclesPanel, javax.swing.BoxLayout.Y_AXIS));

        dispatchedVehiclesScrollPanel.setBackground(new java.awt.Color(255, 255, 255));

        innerDispatchedVehiclesScrollPanel.setBackground(new java.awt.Color(255, 255, 255));
        innerDispatchedVehiclesScrollPanel.setLayout(new javax.swing.BoxLayout(innerDispatchedVehiclesScrollPanel, javax.swing.BoxLayout.Y_AXIS));
        dispatchedVehiclesScrollPanel.setViewportView(innerDispatchedVehiclesScrollPanel);

        vehiclesPanel.add(dispatchedVehiclesScrollPanel);

        lowerPanel.add(vehiclesPanel);

        getContentPane().add(lowerPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectVehiclesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectVehiclesButtonActionPerformed
        onSelectVehiclesBtnClick();
    }//GEN-LAST:event_selectVehiclesButtonActionPerformed

    private void saveVehiclesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveVehiclesButtonActionPerformed
        onSelectVehiclesSave();
    }//GEN-LAST:event_saveVehiclesButtonActionPerformed

    private void dispatchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dispatchButtonActionPerformed
        onDispatch();
    }//GEN-LAST:event_dispatchButtonActionPerformed

    public static void create() {
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            DispatchUi ui = new DispatchUi();
            ui.setVisible(true);

            UiRegistry.add(ui);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressField;
    private javax.swing.JPanel addressPanel;
    private javax.swing.JLabel adressLabel;
    private javax.swing.JTextField callerNumberField;
    private javax.swing.JLabel callerNumberLabel;
    private javax.swing.JPanel callerNumberPanel;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JButton dispatchButton;
    private javax.swing.JScrollPane dispatchedVehiclesScrollPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JTextField infoTextField;
    private javax.swing.JLabel infoTextLabel;
    private javax.swing.JPanel infoTextPanel;
    private javax.swing.JPanel innerDispatchedVehiclesScrollPanel;
    private javax.swing.JPanel innerSelectVehiclesScrollPanel;
    private javax.swing.JComboBox<String> keywordComboBox;
    private javax.swing.JLabel keywordLabel;
    private javax.swing.JPanel keywordPanel;
    private javax.swing.JPanel lowerPanel;
    private javax.swing.JButton saveVehiclesButton;
    private javax.swing.JButton selectVehiclesButton;
    private javax.swing.JDialog selectVehiclesDialog;
    private javax.swing.JScrollPane selectVehiclesScrollPanel;
    private javax.swing.JPanel toolPanel;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JPanel vehiclesPanel;
    // End of variables declaration//GEN-END:variables
}
