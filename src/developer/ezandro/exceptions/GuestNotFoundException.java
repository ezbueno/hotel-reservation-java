package developer.ezandro.exceptions;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(int id) {
        super(String.format("%nERROR: Guest with ID %d not found.", id));
    }
}