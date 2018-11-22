package azkaban.jobtype;

import azkaban.jobExecutor.AbstractJob;
import azkaban.utils.Props;
import azkaban.utils.PropsUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

public class EmailJob extends AbstractJob {

    public static final String MAIL_SUBJECT = "mail.subject";
    public static final String MAIL_MESSAGE = "mail.message";
    public static final String MAIL_LIST = "mail.to";
    public static final String MAIL_HOST = "mail.host";
    public static final String MAIL_SENDER = "mail.sender";
    public static final String MAIL_PORT = "mail.port";
    public static final String MAIL_SEND = "mail.send";
    public static final int DEFAULT_SMTP_PORT = 25;
    private final String mailHost;
    private final String mailSender;
    private final int mailPort;

    protected volatile Props jobProps;
    protected volatile Props sysProps;

    public EmailJob(final String jobId, final Props sysProps, final Props jobProps, final Logger log) {
        super(jobId, log);

        this.jobProps = jobProps;
        this.sysProps = sysProps;
        this.mailHost = sysProps.getString(MAIL_HOST, "localhost");
        this.mailSender = sysProps.getString(MAIL_SENDER, "");
        this.mailPort = sysProps.getInt(MAIL_PORT, DEFAULT_SMTP_PORT);
    }

    @Override
    public void run() {
        resolveProps();

        if (!this.jobProps.getBoolean(MAIL_SEND, true)) {
            info(MAIL_SEND + " configured to false. Not sending mail and finishing job.");
            return;
        }

        try {
            String subject = this.jobProps.getString(MAIL_SUBJECT);
            String message = this.getBody();
            String[] receivers = (String[]) this.jobProps.getStringList(MAIL_LIST).toArray();

            sendEmail(subject, message, receivers);

            info("Subject: \n" + subject);
            info("Message: \n" + message);
            info("Email sent to: \n" + String.join("\n", receivers));
        } catch (EmailException e) {
            error("Could not send email", e);
        }
    }

    private void sendEmail(String subject, String message, String... receivers) throws EmailException {
        Email email = new SimpleEmail();

        email.setHostName(this.mailHost);
        email.setSmtpPort(this.mailPort);
        email.setFrom(this.mailSender);
        email.setSubject(subject);
        email.setMsg(message);
        email.addTo(receivers);

        email.send();
    }

    protected void resolveProps() {
        this.jobProps = PropsUtils.resolveProps(this.jobProps);
    }

    protected String getBody() {
        String body;
        body = this.jobProps.getString(MAIL_MESSAGE);
        for (int i = 1; this.jobProps.containsKey(MAIL_MESSAGE + "." + i); i++) {
            body = body.concat("\n").concat(this.jobProps.getString(MAIL_MESSAGE + "." + i));
        }

        return body;
    }
}
