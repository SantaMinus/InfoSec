package com.sava.ui;

import com.sava.exception.FileManagerException;
import com.sava.file_manager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

class GUI {
    private JMenuBar menuBar = new JMenuBar();

    private static JTextArea filename = new JTextArea();
    private static JTextArea content = new JTextArea();
    private static final JLabel ENTER_FILE_NAME_LABEL = new JLabel("Enter your file name:");
    private static final JLabel FILE_CONTENT_LABEL = new JLabel("File content:");
    static JFrame frame = new JFrame("def1");
    private FileManager fileManager = new FileManager();

    private static final Logger LOGGER = LoggerFactory.getLogger(GUI.class);

    GUI() {
        // intentionally left blank
    }

    void paint(String login) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu fileMenu = new JMenu("File");
        JMenuItem createFile = new JMenuItem("Create file");
        JMenuItem readFile = new JMenuItem("Read file");
        JMenuItem editFile = new JMenuItem("Edit");
        JMenuItem deleteFile = new JMenuItem("Delete");
        JMenuItem exit = new JMenuItem("Exit");

        addComponents(fileMenu, Arrays.asList(createFile, readFile, editFile, deleteFile, exit));
        menuBar.add(fileMenu);

        addComps(frame);
        frame.pack();
        frame.setVisible(true);

        createFile.addActionListener(arg0 -> fileManager.createFile(filename.getText(), login));
        readFile.addActionListener(arg0 -> {
            try {
                fileManager.readFile(filename.getText(), login, content);
            } catch (FileManagerException e) {
                LOGGER.error("Failed to read file", e);
            }
        });
        editFile.addActionListener(arg0 -> {
            try {
                fileManager.editFile(filename.getText(), login, content);
            } catch (FileManagerException e) {
                LOGGER.error("Failed to edit file", e);
            }
        });
        deleteFile.addActionListener(arg0 -> fileManager.deleteFile(filename.getText(), login));

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

        addComponents(panel, Arrays.asList(menuBar, ENTER_FILE_NAME_LABEL, filename, FILE_CONTENT_LABEL, content));
        pane.add(panel);
    }

    private void addComponents(Container main, List<Component> components) {
        components.forEach(main::add);
    }
}
