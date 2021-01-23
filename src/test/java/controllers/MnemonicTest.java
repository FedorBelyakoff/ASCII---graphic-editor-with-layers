package controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class MnemonicTest extends JFrame {

    private JMenuItem ctrlC;
    private JMenuItem ctrlX;
    private JMenuItem ctrlZ;
    private JMenuItem ctrlShiftZ;

    private JMenu testMenu;
    private JMenuBar menuBar;

    public MnemonicTest() throws HeadlessException {
        setWindowSettings();

        addMenu();

        setAccelerators();

        addMessages();

    }

    private void addMenu() {
        createMenuBar();

        createTestMenu();


        addTestItems();

    }

    private void setAccelerators() {
        int ctrl = KeyEvent.CTRL_MASK;
        int shift = KeyEvent.SHIFT_MASK;

        ctrlC.setAccelerator(KeyStroke.getKeyStroke(
                         KeyEvent.VK_C, ctrl));
        ctrlC.setMnemonic('C');
        ctrlX.setAccelerator(KeyStroke.getKeyStroke(
                         KeyEvent.VK_X, ctrl));
        ctrlZ.setAccelerator(KeyStroke.getKeyStroke(
                         KeyEvent.VK_Z, ctrl));

        ctrlShiftZ.setAccelerator(KeyStroke.getKeyStroke(
                         KeyEvent.VK_Z, ctrl | shift
        ));
    }

    private void addMessages() {
        addMessage("Copy.", ctrlC);
        addMessage("Cut.", ctrlX);
        addMessage("Undo.", ctrlZ);
        addMessage("Redo", ctrlShiftZ);
    }



    private void addMessage(String message, JMenuItem item) {
        item.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                             message, "Menu activated",
                             JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void addTestItems() {
        ctrlC = new JMenuItem("Ctrl C");
        ctrlX = new JMenuItem("Ctrl X");
        ctrlZ = new JMenuItem("Ctrl Z");
        ctrlShiftZ = new JMenuItem("Ctrl Shift Z");

        testMenu.add(ctrlX);
        testMenu.add(ctrlZ);
        testMenu.add(ctrlC);
        testMenu.add(ctrlShiftZ);
    }

    private void createTestMenu() {
        testMenu = new JMenu("TestMenu");
        menuBar.add(testMenu);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
    }

    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setSize(500, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MnemonicTest::new);
    }
}
