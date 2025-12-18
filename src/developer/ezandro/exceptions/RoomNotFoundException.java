package developer.ezandro.exceptions;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(int id) {
        super(String.format("%nERROR: Room with ID %d not found.", id));
    }
}