package com.sava.ui;

import com.sava.authenticator.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Random;

public class MainWindow {
    private static final Random randomizer = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private Authenticator authenticator = new Authenticator();
    private JFrame frame = new JFrame();

    public void signIn() {
        final JTextField usernameField = new JTextField(28);
        usernameField.setToolTipText("Username");

        final JPasswordField passwordField = new JPasswordField(28);
        passwordField.setToolTipText("Password");

        final JTextField captcha = new JTextField(28);
        captcha.setToolTipText("Captcha");

        final JPanel loginPanel = new JPanel();
        loginPanel.add(new JLabel("Name:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);

        final int c1 = (randomizer.nextInt(50));
        final int c2 = (randomizer.nextInt(50));
        loginPanel.add(new JLabel("Captcha:    " + c1 + "+" + c2));
        loginPanel.add(captcha);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(arg0 -> {
            boolean accessGranted = authenticator.authenticate(captcha.getText(), usernameField.getText(),
                    passwordField.getPassword(), c1, c2);
            if (accessGranted) {
                frame.dispose();
                GUI gui = new GUI();
                gui.paint(usernameField.getText());
            } else {
                JOptionPane.showMessageDialog(GUI.frame, "Authentication failed", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                LOGGER.debug("An attempt of unauthorised access");
                System.exit(0);
            }
        });
        loginPanel.add(okButton);

        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 90, 350, 250);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
