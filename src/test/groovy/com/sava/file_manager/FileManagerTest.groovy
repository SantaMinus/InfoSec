package com.sava.file_manager

import spock.lang.Specification

import javax.swing.JTextArea

class FileManagerTest extends Specification {
    def fileManager = new FileManager()
    def filename = 'file.txt'
    def login = 'user'

    def "readFile() returns contents of a file"() {
        given:
        def context = new JTextArea()
        context.setText('test')
        def file = new File(getClass().getResource('/file.txt').toURI())
        fileManager.file = file

        when:
        def res = fileManager.readFile(filename, login, context)

        then:
        res == 'sample text\nabc\n'
        context.getText() == 'sample text\nabc\n'
    }
}
