package com.sava.file_manager;

import com.sava.exception.FileManagerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.JTextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class FileManager {
    private static final String ROOT_DIR = "F:/root";
    private static final String FILE_NOT_FOUND_ERROR = "File not found";
    private File file;

    public void createFile(String filename, String login) {
        file = new File(StringUtils.join(Arrays.asList(ROOT_DIR, login, filename), "/"));
        boolean fileCreated;
        try {
            fileCreated = file.createNewFile();
        } catch (IOException e) {
            log.error("Failed to create a new file {}", filename, e);
            return;
        }
        if (fileCreated) {
            log.debug("{} created a file {}", login, filename);
        } else {
            log.debug("A file with such name already exists");
        }
    }

    public void deleteFile(String filename, String login) throws FileManagerException {
        if (file == null) {
            file = new File(StringUtils.join(Arrays.asList(ROOT_DIR, login, filename), "/"));
        }
        if (file.exists()) {
            try {
                Files.delete(Paths.get(file.getPath()));
            } catch (IOException e) {
                throw new FileManagerException(e);
            }
        }
    }

    public void editFile(String filename, String login, JTextArea content) throws FileManagerException {
        if (file.exists()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e1) {
                log.error(FILE_NOT_FOUND_ERROR, e1);
            }
            if (writer == null) {
                throw new FileManagerException("Failed to create a file writer");
            }
            List inputStrings = Arrays.asList(content.getText().split("\n"));
            inputStrings.forEach(writer::println);

            writer.close();
        }
        log.debug("{} deleted a file {}", login, filename);
    }

    public String readFile(String filename, String login, JTextArea content) throws FileManagerException {
        if (file == null) {
            file = new File(StringUtils.join(Arrays.asList(ROOT_DIR, login, filename), "/"));
        }
        StringBuilder s = new StringBuilder();
        if (file.exists()) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                log.error(FILE_NOT_FOUND_ERROR, e);
            }
            if (scanner == null) {
                throw new FileManagerException("Failed to create a file scanner");
            }
            while (scanner.hasNext()) {
                s.append(scanner.nextLine());
                s.append("\n");
            }
            scanner.close();
        }
        content.setText(s.toString());
        return s.toString();
    }

    public File getFile() {
        return file;
    }

    /**
     * For test purpose
     */
    public void setFile(File file) {
        this.file = file;
    }
}
