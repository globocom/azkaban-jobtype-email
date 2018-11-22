# Auror Azkaban Jobtype Email
Send emails with this jobtype for Azkaban Auror

## Install
```
pip install auror_azkaban_jobtype_email
```

## Usage

It works with Azkaban flow v1 or flow v2

```
from auror_azkaban_jobtype_email.v1.job import Email ## or from auror_azkaban_jobtype_email.v2.job import Email

email_job = Email()\
.with_subject("subject")\
.with_message("my message")\
.with_to_recipient("someone@mail.com")\
.with_send()

```
