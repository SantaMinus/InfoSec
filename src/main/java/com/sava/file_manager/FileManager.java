package com.sava.file_manager;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
    private File file;
    private static final String ROOT_DIR = "F:/root";
    private static final String FILE_NOT_FOUND_ERROR = "File not found";

    public void createFile(String filename, String login) {
        file = new File(ROOT_DIR + "/" + login + "/" + filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOGGER.error("Failed to create a new file {}", filename, e);
            return;
        }
        LOGGER.debug("{} created a file {}", login, filename);
    }

    public void editFile(String filename, String login, JTextArea content) {
        if (file == null) {
            file = new File(ROOT_DIR + "/" + login + "/" + filename);
        }

        if (file.exists()) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                LOGGER.error(FILE_NOT_FOUND_ERROR, e);
            }
            String s = null;
            if (scanner.hasNext()) {
                s = scanner.nextLine();
            }
            scanner.close();
            if (!StringUtils.isEmpty(s)) {
                content.setText(s + content.getText());
            }
        }
        LOGGER.debug("{} changed {} file content", login, filename);
    }

    public void deleteFile(String filename, String login) {
        if (file == null) {
            file = new File(ROOT_DIR + "/" + login + "/" + filename);
        }
        if (file.exists()) file.delete();
    }

    // TODO: add this action to edit, don't use separately
    public void confirmEdit(String filename, String login, JTextArea content) {
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
    }
}
