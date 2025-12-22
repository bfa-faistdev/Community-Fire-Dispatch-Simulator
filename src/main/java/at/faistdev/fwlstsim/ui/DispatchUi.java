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
import javax.swing.JPanel;

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
        hideComponents();
        selectedOperation = null;

        startRefreshThreads();
    }

    private void hideComponents() {
        demoSelectVehicle.setVisible(false);

        progressPanel.setVisible(false);
    }

    private void startRefreshThreads() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException ex) {
                    System.getLogger(DispatchUi.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }

                if (selectedOperation == null) {
                    continue;
                }

                if (dispatchButton.isEnabled()) {
                    continue;
                }

                loadAllDispatchedVehicles();
            }
        }, "DispatchUi-LoadAllDispatchedVehicles").start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException ex) {
                    System.getLogger(DispatchUi.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }

                if (selectedOperation == null) {
                    continue;
                }

                updateProgress();
            }
        }, "DispatchUi-UpdateProgress").start();
    }

    public void setSelectedOperation(Operation operation) {
        selectedOperation = operation;
        System.out.println("Selected operation: " + selectedOperation.getCallingNumber());

        if (operation == null) {
            progressPanel.setVisible(false);
            selectVehiclesButton.setEnabled(false);
        } else {
            selectVehiclesButton.setEnabled(true);
        }

        loadAllDispatchedVehicles();
        fillData();
        updateProgress();
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

        innerDispatchedVehiclesScrollPanel.revalidate();
        innerDispatchedVehiclesScrollPanel.repaint();
    }

    private void updateProgress() {
        if (selectedOperation == null) {
            progressPanel.setVisible(false);
            return;
        }

        if (selectedOperation.getVehicles().isEmpty()) {
            progressPanel.setVisible(false);
            return;
        } else {
            progressPanel.setVisible(true);
        }

        int progressInPercent = selectedOperation.getProgressInPercent();
        progressBar.setString(progressInPercent + " %");
        progressBar.setValue(progressInPercent);

        System.out.println("Progress = " + progressInPercent);
    }

    private void addVehicleToDispatchedPanel(Vehicle vehicle) {
        if (isAlreadyInDispatchedPanel(vehicle)) {
            return;
        }

        VehiclePanel panel = createDispatchedVehiclePanel(vehicle);
        innerDispatchedVehiclesScrollPanel.add(panel);

        innerDispatchedVehiclesScrollPanel.revalidate();
        innerDispatchedVehiclesScrollPanel.repaint();

        dispatchButton.setEnabled(true);
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

    private VehiclePanel createVehiclePanel(Vehicle vehicle) {
        VehiclePanel panel = new VehiclePanel(vehicle);
        JPanel statusPanel = new JPanel();
        JLabel statusLabel = new JLabel();
        JLabel vehicleLabel = new JLabel();

        panel.setMaximumSize(new java.awt.Dimension(32767, 23));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.X_AXIS));

        statusPanel.setBackground(VehicleStatusUtil.getColor(vehicle.getStatus()));
        statusPanel.setMaximumSize(new java.awt.Dimension(50, 32767));
        statusPanel.setMinimumSize(new java.awt.Dimension(50, 16));
        statusPanel.setPreferredSize(new java.awt.Dimension(50, 16));
        statusPanel.setLayout(new java.awt.GridLayout());
        panel.add(statusPanel);

        statusLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(0, 0, 0));
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText(vehicle.getStatus().getText());
        statusPanel.add(statusLabel);

        vehicleLabel.setText(vehicle.getName());
        vehicleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        vehicleLabel.setMaximumSize(new java.awt.Dimension(1000, 16));
        vehicleLabel.setMinimumSize(new java.awt.Dimension(300, 16));
        vehicleLabel.setPreferredSize(new java.awt.Dimension(300, 16));
        panel.add(vehicleLabel);

        return panel;
    }

    private VehiclePanel createSelectVehiclePanel(Vehicle vehicle) {
        VehiclePanel panel = createVehiclePanel(vehicle);
        JCheckBox checkBox = new JCheckBox();
        javax.swing.Box.Filler filler = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));

        checkBox.setEnabled(isVehicleAllowedToBeDispatched(vehicle));
        panel.add(checkBox);
        panel.add(filler);

        return panel;
    }

    private boolean isVehicleAllowedToBeDispatched(Vehicle vehicle) {
        return vehicle.getStatus() == VehicleStatus.STATUS_9 && OperationService.isVehicleDispatched(vehicle) == false;
    }

    private VehiclePanel createDispatchedVehiclePanel(Vehicle vehicle) {
        VehiclePanel panel = createVehiclePanel(vehicle);

        JCheckBox checkBox = new JCheckBox();
        javax.swing.Box.Filler filler = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));

        checkBox.setEnabled(false);
        checkBox.setSelected(OperationService.isVehicleDispatched(vehicle));
        panel.add(checkBox);
        panel.add(filler);

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

    private void onDispatch() {
        if (selectedOperation == null) {
            return;
        }

        Set<Vehicle> vehiclesToDispatch = getDispatchedVehiclesFromDialog();
        OperationService.dispatchVehicles(selectedOperation, vehiclesToDispatch);
        loadAllDispatchedVehicles();
        dispatchButton.setEnabled(false);

        updateProgress();
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
        demoSelectVehicle = new javax.swing.JPanel();
        demoSVstatus = new javax.swing.JPanel();
        demoSVstatusText = new javax.swing.JLabel();
        demoSVlabel = new javax.swing.JLabel();
        demoSVcheckbox = new javax.swing.JCheckBox();
        demoSVfiller = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        toolPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        selectVehiclesButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(500, 0));
        dispatchButton = new javax.swing.JButton();
        lowerPanel = new javax.swing.JPanel();
        dataPanel = new javax.swing.JPanel();
        progressPanel = new javax.swing.JPanel();
        progressLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
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

        innerSelectVehiclesScrollPanel.setLayout(new javax.swing.BoxLayout(innerSelectVehiclesScrollPanel, javax.swing.BoxLayout.Y_AXIS));

        demoSelectVehicle.setMaximumSize(new java.awt.Dimension(32767, 23));
        demoSelectVehicle.setLayout(new javax.swing.BoxLayout(demoSelectVehicle, javax.swing.BoxLayout.X_AXIS));

        demoSVstatus.setBackground(new java.awt.Color(0, 153, 51));
        demoSVstatus.setMaximumSize(new java.awt.Dimension(50, 32767));
        demoSVstatus.setMinimumSize(new java.awt.Dimension(50, 16));
        demoSVstatus.setPreferredSize(new java.awt.Dimension(50, 16));
        demoSVstatus.setLayout(new java.awt.GridLayout());

        demoSVstatusText.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        demoSVstatusText.setForeground(new java.awt.Color(0, 0, 0));
        demoSVstatusText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        demoSVstatusText.setText("6");
        demoSVstatus.add(demoSVstatusText);

        demoSelectVehicle.add(demoSVstatus);

        demoSVlabel.setText("TLF 1000 Blumegg Teipl");
        demoSVlabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        demoSVlabel.setMaximumSize(new java.awt.Dimension(1000, 16));
        demoSVlabel.setMinimumSize(new java.awt.Dimension(300, 16));
        demoSVlabel.setPreferredSize(new java.awt.Dimension(300, 16));
        demoSelectVehicle.add(demoSVlabel);
        demoSelectVehicle.add(demoSVcheckbox);
        demoSelectVehicle.add(demoSVfiller);

        innerSelectVehiclesScrollPanel.add(demoSelectVehicle);

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

        progressPanel.setMaximumSize(new java.awt.Dimension(10000, 50));
        progressPanel.setLayout(new javax.swing.BoxLayout(progressPanel, javax.swing.BoxLayout.Y_AXIS));

        progressLabel.setLabelFor(addressField);
        progressLabel.setText("Fortschritt");
        progressLabel.setMaximumSize(new java.awt.Dimension(1000, 16));
        progressPanel.add(progressLabel);

        progressBar.setAlignmentX(0.0F);
        progressBar.setMaximumSize(new java.awt.Dimension(32767, 34));
        progressBar.setMinimumSize(new java.awt.Dimension(10, 34));
        progressBar.setPreferredSize(new java.awt.Dimension(146, 34));
        progressBar.setString("0 %");
        progressBar.setStringPainted(true);
        progressPanel.add(progressBar);

        dataPanel.add(progressPanel);

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
    private javax.swing.JCheckBox demoSVcheckbox;
    private javax.swing.Box.Filler demoSVfiller;
    private javax.swing.JLabel demoSVlabel;
    private javax.swing.JPanel demoSVstatus;
    private javax.swing.JLabel demoSVstatusText;
    private javax.swing.JPanel demoSelectVehicle;
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
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JButton saveVehiclesButton;
    private javax.swing.JButton selectVehiclesButton;
    private javax.swing.JDialog selectVehiclesDialog;
    private javax.swing.JScrollPane selectVehiclesScrollPanel;
    private javax.swing.JPanel toolPanel;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JPanel vehiclesPanel;
    // End of variables declaration//GEN-END:variables
}
