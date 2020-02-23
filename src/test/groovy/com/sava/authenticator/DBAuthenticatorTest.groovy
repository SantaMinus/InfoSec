package com.sava.authenticator

import com.sava.db.UserDao
import com.sava.entity.User
import com.sava.exception.AuthenticatorException
import spock.lang.Specification

class DBAuthenticatorTest extends Specification {
    def userDao = Mock(UserDao)
    def authenticator = new DBAuthenticator(userDao)

    def "authenticate() throws an AuthenticatorException when a user is not found"() {
        given:
        def pass = ['1', '2', '3'] as char[]

        when:
        authenticator.authenticate('50', 'test', pass, 10, 40)

        then:
        AuthenticatorException e = thrown()
        e.message == 'User test is not found'
    }

    def "authenticate() returns true only when both password & captcha are correct"() {
        given:
        def pass = password as char[]
        userDao.getByLogin('test') >> new User(login: 'test', password: '123')

        expect:
        authenticator.authenticate('12', 'test', pass, num1, num2) == auth

        where:
        password        | num1 | num2 | auth
        ['1', '2', '3'] | 5    | 7    | true
        ['1', '2', '3'] | 61   | 8    | false
        ['4', '5', '6'] | 5    | 7    | false
        ['4', '5', '6'] | 61   | 8    | false
    }
}
