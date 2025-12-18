package developer.ezandro.exceptions;

public class RoomAlreadyExistsException extends RuntimeException {
    public RoomAlreadyExistsException(String roomNumber, String existingRoomId) {
        super(String.format(
                "%nERROR: Cannot create room with number '%s'. " +
                        "This number is already assigned to room ID: %s. ",
                roomNumber, existingRoomId
        ));
    }
}