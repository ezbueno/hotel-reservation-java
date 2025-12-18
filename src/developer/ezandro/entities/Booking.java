package developer.ezandro.entities;

import module java.base;

public record Booking(
        int id,
        Room room,
        Guest guest,
        LocalDate checkInDate,
        LocalDate checkOutDate) {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    public Booking(Room room, Guest guest, LocalDate checkInDate, LocalDate checkOutDate) {
        this(ID_GENERATOR.getAndIncrement(), room, guest, checkInDate, checkOutDate);
    }
}
