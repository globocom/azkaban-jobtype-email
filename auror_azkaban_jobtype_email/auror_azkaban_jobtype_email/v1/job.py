from auror_core.v1.job import Job

class Email(Job):
    _type = "email"

    def with_subject(self, mail_subject):
        return self.with_(**{"mail.subject": mail_subject})

    def with_message(self, mail_message):
        return self.with_(**{"mail.message": mail_message})

    def with_to_recipient(self, mail_to):
        return self.with_(**{"mail.to": mail_to})

    def message_with_broken_lines(self, mail_message):
        counter = 1
        while self.extra.get("mail.message.{}".format(counter)):
            counter += 1
        return self.with_(**{"mail.message.{}".format(counter): mail_message})

    def with_send(self, mail_send="true"):
        return self.with_(**{"mail.send": mail_send})