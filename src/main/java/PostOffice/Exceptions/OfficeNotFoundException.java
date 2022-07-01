package PostOffice.Exceptions;

public class OfficeNotFoundException extends RuntimeException {
    public OfficeNotFoundException(Long index) {
        super(String.format("Office with index [%s] is not found", index));
    }
}
