package developer.ezandro.exceptions;

public class RoomAlreadyBookedException extends RuntimeException {
    public RoomAlreadyBookedException(String roomNumber, int roomId) {
        super(String.format(
                "%nERROR: Room %s (ID %d) is already booked for the selected period.",
                roomNumber,
                roomId
        ));
    }
}