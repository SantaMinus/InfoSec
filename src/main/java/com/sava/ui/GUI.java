package com.sava.ui;

import com.sava.file_manager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class GUI {
    private JMenuBar menuBar = new JMenuBar();

    private static JTextArea fname = new JTextArea();
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
        //frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //menuBar
        JMenu fmenu = new JMenu("File");
        JMenuItem createFile = new JMenuItem("Create file");
        JMenuItem readFile = new JMenuItem("Read file");
        JMenuItem editFile = new JMenuItem("Edit");
        JMenuItem deleteFile = new JMenuItem("Delete");
        JMenuItem exit = new JMenuItem("Exit");
        fmenu.add(createFile);
        fmenu.add(readFile);
        fmenu.add(editFile);
        fmenu.add(deleteFile);
        fmenu.add(exit);
        menuBar.add(fmenu);

        //adding components
        addComps(frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        createFile.addActionListener(arg0 -> fileManager.createFile(fname.getText(), login));
        readFile.addActionListener(arg0 -> fileManager.readFile(fname.getText(), login, content));
        editFile.addActionListener(arg0 -> fileManager.editFile(fname.getText(), login, content));
        deleteFile.addActionListener(arg0 -> fileManager.deleteFile(fname.getText(), login));

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
