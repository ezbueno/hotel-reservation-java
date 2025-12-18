package developer.ezandro.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(int id) {
        super(String.format("%nERROR: Booking with ID %d not found.", id));
    }
}