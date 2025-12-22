/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package at.faistdev.fwlstsim.ui;

import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationStatus;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ben
 */
public class CallTakerUi extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CallTakerUi.class.getName());
    private static final Color CURRENT_CALL_COLOR = Color.green;

    private Operation currentTalkingOperation;

    /**
     * Creates new form CallTakerUi
     */
    public CallTakerUi() {
        initComponents();

        new Thread(() -> {
            while (true) {
                refreshIncomingCalls();
                System.out.println("refreshIncomingCalls");
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException ex) {
                    System.getLogger(CallTakerUi.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        }).start();

    }

    private synchronized void refreshIncomingCalls() {
        incomingCallsPanel.removeAll();
        List<Operation> unansweredOperations = OperationService.getUnansweredOperations();
        for (Operation operation : unansweredOperations) {
            JPanel newCallPanel = createNewIncomingCallElement(operation.getCallingNumber());
            if (operation.equals(currentTalkingOperation)) {
                newCallPanel.setBackground(CURRENT_CALL_COLOR);
            }
            incomingCallsPanel.add(newCallPanel);
        }

        incomingCallsPanel.revalidate();
        incomingCallsPanel.repaint();
    }

    private JPanel getSingleIncomingCallPanel(String callNumber) {
        Component[] callPanels = incomingCallsPanel.getComponents();
        for (Component cp : callPanels) {
            JPanel callPanel = (JPanel) cp;
            Component l = callPanel.getComponent(0);
            JLabel label = (JLabel) l;
            if (label.getText().equals(callNumber)) {
                return callPanel;
            }
        }

        return null;
    }

    private void setIncomingCallColor(String callText, Color color) {
        JPanel panel = getSingleIncomingCallPanel(callText);
        if (panel == null) {
            return;
        }

        panel.setBackground(color);
    }

    private void onAnswerCallClick() {
        List<Operation> unansweredOperations = OperationService.getUnansweredOperations();
        if (unansweredOperations.isEmpty()) {
            return;
        }

        Operation operation = unansweredOperations.get(0);
        insertCallText(operation.getCallText());
        btnAnswerCall.setEnabled(false);
        btnEndCall.setEnabled(true);
        currentTalkingOperation = operation;
        setIncomingCallColor(operation.getCallingNumber(), CURRENT_CALL_COLOR);

        DispatchUi dispatchUi = UiRegistry.get(DispatchUi.class);
        dispatchUi.setSelectedOperation(operation);
    }

    private void onEndCallClick() {
        insertAnswerText("Rettungskräfte sind auf dem Weg. Machen Sie sich bemerkbar. Tschüss.");
        currentTalkingOperation.setStatus(OperationStatus.CALL_ANSWERED);
        btnAnswerCall.setEnabled(true);
        btnEndCall.setEnabled(false);
        currentTalkingOperation = null;
        refreshIncomingCalls();
    }

    private void insertCallText(String text) {
        JPanel panel = getCallTextPanel();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        JLabel label = getCallTextLabel(text);
        label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        panel.add(label);
        talkingPanel.add(panel);

        talkingPanel.revalidate();
        talkingPanel.repaint();
    }

    private void insertAnswerText(String text) {
        JPanel panel = getCallTextPanel();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));

        JLabel label = getCallTextLabel(text);
        label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        panel.add(label);
        talkingPanel.add(panel);

        talkingPanel.revalidate();
        talkingPanel.repaint();
    }

    private JPanel getCallTextPanel() {
        JPanel panel = new JPanel();
        panel.setMaximumSize(new java.awt.Dimension(32767, 50));
        panel.setLayout(new java.awt.GridLayout());

        return panel;
    }

    private JLabel getCallTextLabel(String text) {
        JLabel label = new JLabel();
        label.setText("<html>" + text + "</html>");
        return label;
    }

    private JPanel createNewIncomingCallElement(String callingNumber) {
        JPanel callPanel = new JPanel();
        callPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1)));
        callPanel.setMaximumSize(new java.awt.Dimension(32767, 50));
        callPanel.setLayout(new java.awt.GridLayout());

        JLabel label = new JLabel();
        label.setText(callingNumber);
        callPanel.add(label);

        return callPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        incomingCallsPanel = new javax.swing.JPanel();
        callPromptsPanel = new javax.swing.JPanel();
        callPromptsInnerPanel = new javax.swing.JPanel();
        btnEndCall = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        connectedCallPanel = new javax.swing.JPanel();
        actionPanel = new javax.swing.JPanel();
        btnAnswerCall = new javax.swing.JButton();
        talkingPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Anrufe");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(960, 540));
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        leftPanel.setBackground(new java.awt.Color(255, 255, 255));
        leftPanel.setLayout(new java.awt.GridLayout(2, 0));

        incomingCallsPanel.setLayout(new javax.swing.BoxLayout(incomingCallsPanel, javax.swing.BoxLayout.Y_AXIS));
        leftPanel.add(incomingCallsPanel);

        callPromptsPanel.setLayout(new javax.swing.BoxLayout(callPromptsPanel, javax.swing.BoxLayout.Y_AXIS));

        callPromptsInnerPanel.setBackground(new java.awt.Color(255, 255, 255));
        callPromptsInnerPanel.setMaximumSize(new java.awt.Dimension(32767, 50));
        callPromptsInnerPanel.setLayout(new java.awt.GridLayout(1, 0));

        btnEndCall.setText("Rettungskräfte auf dem Weg");
        btnEndCall.setEnabled(false);
        btnEndCall.addActionListener(this::btnEndCallActionPerformed);
        callPromptsInnerPanel.add(btnEndCall);

        callPromptsPanel.add(callPromptsInnerPanel);

        leftPanel.add(callPromptsPanel);

        getContentPane().add(leftPanel);

        rightPanel.setBackground(new java.awt.Color(255, 255, 255));
        rightPanel.setLayout(new java.awt.GridLayout(1, 0));

        connectedCallPanel.setLayout(new javax.swing.BoxLayout(connectedCallPanel, javax.swing.BoxLayout.Y_AXIS));

        actionPanel.setBackground(new java.awt.Color(255, 255, 255));
        actionPanel.setMaximumSize(new java.awt.Dimension(32767, 50));
        actionPanel.setLayout(new java.awt.GridLayout(1, 0));

        btnAnswerCall.setText("Anruf annehmen");
        btnAnswerCall.setMaximumSize(new java.awt.Dimension(120, 50));
        btnAnswerCall.setPreferredSize(new java.awt.Dimension(120, 50));
        btnAnswerCall.addActionListener(this::btnAnswerCallActionPerformed);
        actionPanel.add(btnAnswerCall);

        connectedCallPanel.add(actionPanel);

        talkingPanel.setBackground(new java.awt.Color(255, 255, 255));
        talkingPanel.setLayout(new javax.swing.BoxLayout(talkingPanel, javax.swing.BoxLayout.Y_AXIS));
        connectedCallPanel.add(talkingPanel);

        rightPanel.add(connectedCallPanel);

        getContentPane().add(rightPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnswerCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnswerCallActionPerformed
        onAnswerCallClick();
    }//GEN-LAST:event_btnAnswerCallActionPerformed

    private void btnEndCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndCallActionPerformed
        onEndCallClick();
    }//GEN-LAST:event_btnEndCallActionPerformed

    public static void create() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            CallTakerUi ui = new CallTakerUi();
            ui.setVisible(true);

            UiRegistry.add(ui);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton btnAnswerCall;
    private javax.swing.JButton btnEndCall;
    private javax.swing.JPanel callPromptsInnerPanel;
    private javax.swing.JPanel callPromptsPanel;
    private javax.swing.JPanel connectedCallPanel;
    private javax.swing.JPanel incomingCallsPanel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JPanel talkingPanel;
    // End of variables declaration//GEN-END:variables
}
