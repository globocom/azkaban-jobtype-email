# Azkaban jobtype Email
Email Plugin for [Azkaban](https://github.com/azkaban/azkaban)

## Build

Execute the command:

```sh
./gradlew installDist
```

## Installing

After completion, plugins will be available in `build/install/azkaban-jobtype-email`. Move `plugins` directory to `$AZKABAN_HOME` dir (Executor Server). Configure `.properties` files as necessary.
If you already have `$AZKABAN_HOME/plugins` dir on Azkaban Executor Server, just add the necessary files (jars on `plugins/extlib` and configuration files on `plugins/jobtypes/email`)

## Usage

#### Admin:

Configurations:

In `private.properties`
```
jobtype.class=azkaban.jobtype.EmailJob

mail.sender=sender@gmail.com
mail.host=localhost
mail.port=25
```

#### User:

```
type=email

# If false, it will not send the email
# Useful parameter when this job is on a flow, and the previous job wants to abort email
# So, just pass mail.send=false
# true is default value
mail.send=true

mail.subject=Email subject
mail.message=Email body
mail.message.1=This is a new line on email
mail.message.2=New lines are optional, just mail.message is mandatory
mail.message.3=You can add how many lines you want
mail.to=recipient@gmail.com
```