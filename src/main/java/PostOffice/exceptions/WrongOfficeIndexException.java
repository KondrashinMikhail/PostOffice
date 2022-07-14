package PostOffice.exceptions;

public class WrongOfficeIndexException extends RuntimeException {
    public WrongOfficeIndexException() {
        super("The recipient index and actual are not the same.");
    }
}
