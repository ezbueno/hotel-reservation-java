package developer.ezandro.services;

import module java.base;
import developer.ezandro.entities.Room;
import developer.ezandro.entities.RoomType;
import developer.ezandro.exceptions.RoomAlreadyExistsException;
import developer.ezandro.repositories.RoomRepository;

public record RoomService(RoomRepository roomRepository) {
    public void addRoom(String number, int capacity, RoomType type) {
        this.roomRepository.addRoom(new Room(number, capacity, type));
    }

    public void validateRoomNumberAvailable(String number) {
        this.roomRepository.findRoomByNumber(number)
                .ifPresent(existingRoom -> {
                    throw new RoomAlreadyExistsException(
                            number,
                            String.valueOf(existingRoom.id())
                    );
                });
    }

    public Room findRoomById(int id) {
        return this.roomRepository.findRoomById(id);
    }

    public List<Room> getAllRooms() {
        return this.roomRepository.getAllRooms();
    }
}