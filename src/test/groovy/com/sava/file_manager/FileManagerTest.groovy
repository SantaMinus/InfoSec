package com.sava.file_manager

import spock.lang.Specification

import javax.swing.*

class FileManagerTest extends Specification {
    def fileManager = new FileManager()
    def login = 'user'

    def "readFile() returns contents of a file"() {
        given:
        def context = new JTextArea()
        context.setText('test')
        def file = new File(getClass().getResource('/readFile.txt').toURI())
        fileManager.file = file

        when:
        def res = fileManager.readFile('readFile.txt', login, context)

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
        fileManager.editFile(login, context)

        then:
        file.getText() == 'updated\r\ntext\r\n'
    }
}
