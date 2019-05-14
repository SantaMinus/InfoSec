package com.sava.ui;

import com.sava.application.Main;
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

public class GUI {
    private JMenuBar menuBar = new JMenuBar();
    private static String filename = "";
    private static File file;
    private static JTextArea fname = new JTextArea();
    private static JTextArea content = new JTextArea();
    private static final JLabel ENTER_FILE_NAME_LABEL = new JLabel("Enter your file name:");
    private static final JLabel FILE_CONTENT_LABEL = new JLabel("File content:");
    public static JFrame frame = new JFrame("def1");
    private static final String ROOT_DIR = "../root";
    private static final String FILE_NOT_FOUND_ERROR = "File not found";

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public GUI() {
        // intentionally left blank
    }

    public void paint(String ulogin) {
        //frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //menuBar
        JMenu fmenu = new JMenu("File");
        JMenuItem add = new JMenuItem("Create file");
        JMenuItem edit = new JMenuItem("Edit");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem exit = new JMenuItem("Exit");
        fmenu.add(add);
        fmenu.add(edit);
        fmenu.add(delete);
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
        add.addActionListener(arg0 -> {
            filename = fname.getText();
            file = new File(ROOT_DIR + ulogin + "/" + filename);
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error("Failed to create a new file {}", filename, e);
            }
            LOGGER.debug("{} created a file {}", ulogin, filename);
        });

        //EDIT listener
        edit.addActionListener(arg0 -> {
            if (file == null) {
                filename = fname.getText();
                file = new File(ROOT_DIR + ulogin + "/" + filename);
            }
            content.setText("");
            if (file.exists()) {
                //name.setText(file.getName());
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
            LOGGER.debug("{} changed {} file content", ulogin, filename);
        });

        //DELETE listener
        delete.addActionListener(arg0 -> {
            if (file == null) {
                filename = fname.getText();
                file = new File(ROOT_DIR + ulogin + "/" + filename);
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
            LOGGER.debug("{} deleted a file {}", ulogin, filename);
        });

        //EXIT listener
        exit.addActionListener(arg0 -> {
            LOGGER.debug("{} signed out", ulogin);
            System.exit(0);
        });
    }

    private void addComps(final Container pane) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2, 2));

        //Add buttons to experiment with Grid Layout
        panel.add(menuBar);
        panel.add(ENTER_FILE_NAME_LABEL);
        panel.add(fname);
        panel.add(FILE_CONTENT_LABEL);
        panel.add(content);

        //Process the Apply gaps button press
        pane.add(panel);
    }
}
