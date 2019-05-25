package com.sava.file_manager

import spock.lang.Specification

import javax.swing.JTextArea

class FileManagerTest extends Specification {
    def fileManager = new FileManager()
    def filename = 'readFile.txt'
    def login = 'user'

    def "readFile() returns contents of a file"() {
        given:
        def context = new JTextArea()
        context.setText('test')
        def file = new File(getClass().getResource('/readFile.txt').toURI())
        fileManager.file = file

        when:
        def res = fileManager.readFile(filename, login, context)

        then:
        res == 'sample text\nabc\n'
        context.getText() == 'sample text\nabc\n'
    }

    def "editFile() updates a file with a text from a context field"() {
        given:
        def context = new JTextArea()
        context.setText('updated\ntext')
        def file = new File(getClass().getResource('/editFile.txt').toURI())
        fileManager.file = file

        when:
        fileManager.editFile(filename, login, context)

        then:
        file.getText() == 'updated\r\ntext\r\n'
    }
}
