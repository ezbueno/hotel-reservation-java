package developer.ezandro.exceptions;

public class RoomTypeNotFoundException extends RuntimeException {
    public RoomTypeNotFoundException(int optionNumber) {
        super(String.format(
                "%nERROR: Option '%d' is not a valid room type. " +
                        "Available options: 1 (SINGLE), 2 (DOUBLE), 3 (SUITE)",
                optionNumber
        ));
    }
}