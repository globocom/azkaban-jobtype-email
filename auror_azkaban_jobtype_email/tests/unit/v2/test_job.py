#!/usr/bin/env python
# -*- coding: utf-8 -*-

from unittest import TestCase
from auror_azkaban_jobtype_email.v2.job import Email


class EmailJobTest(TestCase):

    def test_with_subject(self):
        result = Email().with_subject("Exemplo de email")
        actual = result.extra
        expected = {"mail.subject": "Exemplo de email"}

        self.assertEqual("email", result._type)
        self.assertEqual(expected, actual)

    def test_with_message(self):
        result = Email().with_message("Esse é o conteúdo do email")
        actual = result.extra
        expected = {"mail.message": "Esse é o conteúdo do email"}

        self.assertEqual("email", result._type)
        self.assertEqual(expected, actual)

    def test_with_to_recipient(self):
        result = Email().with_to_recipient("exemplo@corp.globo.com")
        actual = result.extra
        expected = {"mail.to": "exemplo@corp.globo.com"}

        self.assertEqual("email", result._type)
        self.assertEqual(expected, actual)

    def test_message_with_broken_lines(self):
        mail_message = "Esse é o conteúdo do email"
        result_ = Email().message_with_broken_lines(mail_message)
        mail_message_2 = "Essa é uma nova linha"
        result_2 = result_.message_with_broken_lines(mail_message_2)
        mail_message_3 = "Pode ser inseridas quantas linhas quiser"
        result_3 = result_2.message_with_broken_lines(mail_message_3)

        actual = result_3.extra
        expected = {"mail.message.1": "Esse é o conteúdo do email",
                    "mail.message.2": "Essa é uma nova linha",
                    "mail.message.3": "Pode ser inseridas quantas linhas quiser"}

        self.assertEqual("email", result_3._type)
        self.assertEqual(expected, actual)

    def test_in_which_email_is_not_sent(self):
        result = Email().with_send("false")
        actual = result.extra
        expected = {"mail.send": "false"}

        self.assertEqual("email", result._type)
        self.assertEqual(expected, actual)
