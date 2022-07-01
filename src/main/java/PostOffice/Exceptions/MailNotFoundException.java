package PostOffice.Exceptions;

public class MailNotFoundException extends RuntimeException {
    public MailNotFoundException(Long id) {
        super(String.format("Mail with id [%s] is not found", id));
    }
}
