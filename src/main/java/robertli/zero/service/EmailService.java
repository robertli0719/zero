package robertli.zero.service;

/**
 * Web site can use this service to send email to users.
 *
 * @version 1.0 2016-08-20
 * @author Robert Li
 */
public interface EmailService {

    /**
     * Send an email out, return the sending result after sending.
     *
     * @param emailMessage the email which will be send out.
     * @return true if sending fail
     */
    public abstract boolean sendWithBlocking(EmailMessage emailMessage);

    /**
     * Send an email out using a new Thread, so that the current thread will not
     * be blocked.
     *
     * @param emailMessage the email which will be send out.
     */
    public abstract void send(EmailMessage emailMessage);

    /**
     * Send an email out using a new Thread, so that the current thread will not
     * be blocked. The invoker can give it a callback function to process
     * result.
     *
     * @param emailMessage the email which will be send out.
     * @param callback
     */
    public abstract void send(EmailMessage emailMessage, SenderCallbacker callback);

    public interface SenderCallbacker {

        /**
         *
         * @param emailMessage the email message which just be send
         * @param fail true if sending fail
         */
        public void processResult(EmailMessage emailMessage, boolean fail);
    }
}
