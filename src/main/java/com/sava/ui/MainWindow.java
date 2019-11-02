package com.sava.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.util.Random;

import com.sava.authenticator.Authenticator;
import com.sava.authenticator.DBAuthenticator;
import com.sava.exception.AuthenticatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainWindow {
    private static final Random randomizer = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);

    private Authenticator authenticator;
    private GUI gui;
    private JFrame frame = new JFrame();

    @Autowired
    public MainWindow(Authenticator authenticator, GUI gui) {
        this.authenticator = authenticator;
        this.gui = gui;
    }

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
            boolean accessGranted = false;
            try {
                accessGranted = authenticator.authenticate(captcha.getText(), usernameField.getText(),
                        passwordField.getPassword(), c1, c2);
            } catch (AuthenticatorException e) {
                LOGGER.error("Failed to sign in", e);
            }
            if (accessGranted) {
                frame.dispose();
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
