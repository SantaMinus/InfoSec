package com.sava.ui;

import com.sava.authenticator.Authenticator;
import com.sava.authenticator.DBAuthenticator;
import com.sava.exception.AuthenticatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.util.Random;

@Slf4j
@Component
public class MainWindow {
    private static final Random randomizer = new Random();
    private Authenticator authenticator = new DBAuthenticator();
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
            boolean accessGranted = false;
            try {
                accessGranted = authenticator.authenticate(captcha.getText(), usernameField.getText(),
                        passwordField.getPassword(), c1, c2);
            } catch (AuthenticatorException e) {
                log.error("Failed to sign in", e);
            }
            if (accessGranted) {
                frame.dispose();
                GUI gui = new GUI();
                gui.paint(usernameField.getText());
            } else {
                JOptionPane.showMessageDialog(GUI.frame, "Authentication failed", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                log.debug("An attempt of unauthorised access");
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
