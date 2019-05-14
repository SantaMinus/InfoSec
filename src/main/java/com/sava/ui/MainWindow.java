package com.sava.ui;

import com.sava.authenticator.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Random;

public class MainWindow extends JFrame {
    private static final Random randomizer = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private Authenticator authenticator = new Authenticator();

    public void signIn() {
        final JPanel login = new JPanel();

        final JTextField name = new JTextField(28);
        final JPasswordField pass = new JPasswordField(28);
        final JTextField captcha = new JTextField(28);

        name.setToolTipText("Name");
        pass.setToolTipText("Password");
        captcha.setToolTipText("Captcha");

        JButton ok = new JButton("OK");

        login.add(new JLabel("Name:"));
        login.add(name);
        login.add(new JLabel("Password:"));
        login.add(pass);
        // TODO: fix randomizers
        final int c1 = (randomizer.nextInt() * 10);
        final int c2 = (randomizer.nextInt() * 10);
        login.add(new JLabel("Captcha:    " + c1 + "+" + c2));
        login.add(captcha);

        login.add(ok);
        this.setContentPane(login);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 90, 350, 250);
        this.setResizable(false);
        this.setVisible(true);

        ok.addActionListener(arg0 -> {
            boolean access = authenticator.authenticate(captcha.getText(), name.getText(), pass.getText(), c1, c2);
            if (access) {
                this.dispose();
                GUI gui = new GUI();
                gui.paint(name.getText());
            } else {
                JOptionPane.showMessageDialog(GUI.frame, "Authentication failed", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                LOGGER.debug("An attempt of unauthorised access");
                System.exit(0);
            }
        });
    }
}
