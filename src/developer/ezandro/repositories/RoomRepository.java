package developer.ezandro.repositories;

import module java.base;
import developer.ezandro.entities.Room;
import developer.ezandro.exceptions.RoomAlreadyExistsException;
import developer.ezandro.exceptions.RoomNotFoundException;

public class RoomRepository {
    private final List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        Optional<Room> existingRoom = this.findRoomByNumber(room.number());

        if (existingRoom.isPresent()) {
            throw new RoomAlreadyExistsException(
                    room.number(),
                    String.valueOf(existingRoom.get().id())
            );
        }

        this.rooms.add(room);
    }

    public Optional<Room> findRoomByNumber(String number) {
        return this.rooms
                .stream()
                .filter(roomNumber -> roomNumber.number().equals(number))
                .findFirst();
    }

    public Room findRoomById(int id) {
        return this.rooms
                .stream()
                .filter(room -> room.id() == id)
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    public List<Room> getAllRooms() {
        return Collections.unmodifiableList(this.rooms);
    }
}