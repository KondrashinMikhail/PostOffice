package PostOffice.Exceptions;

public class MailAlreadyIssuedException extends RuntimeException {
    public MailAlreadyIssuedException (Long id) {
        super(String.format("Mail with id [%s] is already issued, you can not change status", id));
    }
}
