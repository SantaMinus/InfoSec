package com.sava.application;

import com.sava.ui.GUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main extends JFrame {
    public static String ulogin;
    private static final File FILE = new File("access.xml");
    private static final Random randomizer = new Random();

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String FILE_NOT_FOUND_ERROR = "File not found";

    public static void main(String[] args) {
        signIn();
    }

    private static void signIn() {
        final Main m = new Main();
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
        final int c1 = (randomizer.nextInt() * 10);
        final int c2 = (randomizer.nextInt() * 10);
        login.add(new JLabel("Captcha:    " + c1 + "+" + c2));
        login.add(captcha);

        login.add(ok);
        m.setContentPane(login);

        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setBounds(200, 90, 350, 250);
        m.setResizable(false);
        m.setVisible(true);

        ok.addActionListener(arg0 -> {
            ulogin = name.getText();
            int capt = Integer.parseInt(captcha.getText());
            String[] arr;
            boolean access = false;

            Scanner scanner = null;
            try {
                scanner = new Scanner(FILE);
            } catch (FileNotFoundException e) {
                LOGGER.error(FILE_NOT_FOUND_ERROR, e);
            }
            String s;

            while (scanner.hasNext()) {
                s = scanner.nextLine();
                arr = s.split(" ");

                if (ulogin.equals(arr[0]) && pass.getText().equals(arr[1]) && capt == c1 + c2) {
                    File directory = new File("C:/root/" + ulogin);
                    access = true;
                    if (!directory.exists())
                        directory.mkdir();
                    try {
                        Runtime.getRuntime().exec("attrib +H " + "C:/root/" + ulogin);
                    } catch (IOException e) {
                        LOGGER.error("Failed to execute a command", e);
                    }
                    LOGGER.debug("{} signed in", ulogin);
                    m.dispose();
                    GUI.paint();
                }
            }
            if (!access) {
                scanner.close();
                JOptionPane.showMessageDialog(GUI.frame, "Authentication failed", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                LOGGER.debug("An attempt of unauthorised access");
                System.exit(0);
            }
            scanner.close();
        });
    }
}