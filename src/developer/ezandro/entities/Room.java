package developer.ezandro.entities;

import module java.base;

public record Room(
        int id,
        String number,
        int capacity,
        RoomType roomType) {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    public Room(String number, int capacity, RoomType roomType) {
        this(ID_GENERATOR.getAndIncrement(), number, capacity, roomType);
    }
}