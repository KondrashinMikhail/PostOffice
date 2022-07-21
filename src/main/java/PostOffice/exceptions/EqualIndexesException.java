package PostOffice.exceptions;

public class EqualIndexesException extends RuntimeException {
    public EqualIndexesException() {
        super("The recipient index and source are the same.");
    }
}
