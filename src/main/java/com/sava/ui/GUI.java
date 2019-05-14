package com.sava.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class GUI {
    private JMenuBar menuBar = new JMenuBar();
    private String filename = "";
    private File file;
    private static JTextArea fname = new JTextArea();
    private static JTextArea content = new JTextArea();
    private static final JLabel ENTER_FILE_NAME_LABEL = new JLabel("Enter your file name:");
    private static final JLabel FILE_CONTENT_LABEL = new JLabel("File content:");
    static JFrame frame = new JFrame("def1");
    private static final String ROOT_DIR = "../root";
    private static final String FILE_NOT_FOUND_ERROR = "File not found";

    private static final Logger LOGGER = LoggerFactory.getLogger(GUI.class);

    GUI() {
        // intentionally left blank
    }

    void paint(String login) {
        //frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //menuBar
        JMenu fmenu = new JMenu("File");
        JMenuItem createFile = new JMenuItem("Create file");
        JMenuItem editFile = new JMenuItem("Edit");
        JMenuItem deleteFile = new JMenuItem("Delete");
        JMenuItem exit = new JMenuItem("Exit");
        fmenu.add(createFile);
        fmenu.add(editFile);
        fmenu.add(deleteFile);
        fmenu.add(exit);
        menuBar.add(fmenu);

        JMenuItem button = new JMenuItem("Confirm");
        menuBar.add(button);

        //adding components
        addComps(frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        //ADD listener
        createFile.addActionListener(arg0 -> {
            filename = fname.getText();
            file = new File(ROOT_DIR + "/" + login + "/" + filename);
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error("Failed to create a new file {}", filename, e);
                return;
            }
            LOGGER.debug("{} created a file {}", login, filename);
        });

        //EDIT listener
        editFile.addActionListener(arg0 -> {
            if (file == null) {
                filename = fname.getText();
                file = new File(ROOT_DIR + "/" + login + "/" + filename);
            }
            content.setText("");
            if (file.exists()) {
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException e) {
                    LOGGER.error(FILE_NOT_FOUND_ERROR, e);
                }
                String s = null;
                if (scanner.hasNext()) s = scanner.nextLine();
                scanner.close();
                if (s != null) content.setText(content.getText() + s);
            }
            LOGGER.debug("{} changed {} file content", login, filename);
        });

        //DELETE listener
        deleteFile.addActionListener(arg0 -> {
            if (file == null) {
                filename = fname.getText();
                file = new File(ROOT_DIR + login + "/" + filename);
            }
            if (file.exists()) file.delete();
        });

        //CONFIRM listener
        button.addActionListener(e -> {
            if (file.exists()) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(file);
                } catch (FileNotFoundException e1) {
                    LOGGER.error(FILE_NOT_FOUND_ERROR, e1);
                }

                writer.print("");
                writer.print(content.getText());

                writer.close();
            }
            LOGGER.debug("{} deleted a file {}", login, filename);
        });

        //EXIT listener
        exit.addActionListener(arg0 -> {
            LOGGER.debug("{} signed out", login);
            System.exit(0);
        });
    }

    private void addComps(final Container pane) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2, 2));
        //Add buttons
        panel.add(menuBar);
        panel.add(ENTER_FILE_NAME_LABEL);
        panel.add(fname);
        panel.add(FILE_CONTENT_LABEL);
        panel.add(content);

        pane.add(panel);
    }
}
