package com.sava.ui;

import com.sava.application.Main;

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
    private static JMenuBar menu = new JMenuBar();
    private static String filename = "";
    private static File file;
    private static JTextArea fname = new JTextArea();
    private static JTextArea content = new JTextArea();
    private static final JLabel ENTER_FILE_NAME_LABEL = new JLabel("Enter your file name:");
    private static final JLabel FILE_CONTENT_LABEL = new JLabel("File content:");
    public static JFrame frame = new JFrame("def1");
    private static final String ROOT_DIR = "../root";

    private GUI() {
        // intentionally left blank
    }

    public static void paint() {
        //frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //menu
        JMenu fmenu = new JMenu("File");
        menu.add(fmenu);
        JMenuItem add = new JMenuItem("Create file");
        JMenuItem edit = new JMenuItem("Edit");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem exit = new JMenuItem("Exit");
        fmenu.add(add);
        fmenu.add(edit);
        fmenu.add(delete);
        fmenu.add(exit);

        JMenuItem button = new JMenuItem("Confirm");
        menu.add(button);

        //adding components
        addComps(frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        //ADD listener
        add.addActionListener(arg0 -> {
            filename = fname.getText();
            file = new File(ROOT_DIR + Main.ulogin + "/" + filename);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Main.writeLog(Main.ulogin + " created a file " + filename + " at " + Main.DATE.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //EDIT listener
        edit.addActionListener(arg0 -> {
            if (file == null) {
                filename = fname.getText();
                file = new File(ROOT_DIR + Main.ulogin + "/" + filename);
            }
            content.setText("");
            if (file.exists()) {
                //name.setText(file.getName());
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String s = null;
                if (scanner.hasNext()) s = scanner.nextLine();
                scanner.close();
                if (s != null) content.setText(content.getText() + s);
            }
            try {
                Main.writeLog(Main.ulogin + " changed " + filename + " file content at " + Main.DATE.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //DELETE listener
        delete.addActionListener(arg0 -> {
            if (file == null) {
                filename = fname.getText();
                file = new File(ROOT_DIR + Main.ulogin + "/" + filename);
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
                    e1.printStackTrace();
                }

                writer.print("");
                writer.print(content.getText());

                writer.close();
            }
            try {
                Main.writeLog(Main.ulogin + " deleted a file " + filename + " at " + Main.DATE.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        //EXIT listener
        exit.addActionListener(arg0 -> {
            try {
                Main.writeLog(Main.ulogin + " signed out at " + Main.DATE.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
    }

    private static void addComps(final Container pane) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2, 2));

        //Add buttons to experiment with Grid Layout
        panel.add(menu);
        panel.add(ENTER_FILE_NAME_LABEL);
        panel.add(fname);
        panel.add(FILE_CONTENT_LABEL);
        panel.add(content);

        //Process the Apply gaps button press
        pane.add(panel);
    }
}
