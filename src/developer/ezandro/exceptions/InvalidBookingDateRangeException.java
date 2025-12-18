package developer.ezandro.exceptions;

public class InvalidBookingDateRangeException extends RuntimeException {
    public InvalidBookingDateRangeException() {
        super("%nERROR: Check-out date must be after check-in date.");
    }
}