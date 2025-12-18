package developer.ezandro.services;

import module java.base;
import developer.ezandro.entities.Booking;
import developer.ezandro.entities.Guest;
import developer.ezandro.entities.Room;
import developer.ezandro.exceptions.InvalidBookingDateRangeException;
import developer.ezandro.exceptions.RoomAlreadyBookedException;
import developer.ezandro.repositories.BookingRepository;
import developer.ezandro.repositories.GuestRepository;
import developer.ezandro.repositories.RoomRepository;

public record BookingService(
        BookingRepository bookingRepository,
        RoomRepository roomRepository,
        GuestRepository guestRepository) {
    public void createBooking(int roomId, int guestId, LocalDate checkIn, LocalDate checkOut) {
        Room room = this.roomRepository.findRoomById(roomId);
        Guest guest = this.guestRepository.findGuestById(guestId);

        List<Booking> existingBookingsForRoom = this.bookingRepository.getAllBookings().stream()
                .filter(booking -> booking.room().id() == roomId)
                .toList();

        boolean hasConflict = existingBookingsForRoom.stream()
                .anyMatch(existingBooking -> checkIn.isBefore(existingBooking.checkOutDate())
                                && checkOut.isAfter(existingBooking.checkInDate())
                );

        if (hasConflict) {
            throw new RoomAlreadyBookedException(room.number(), roomId);
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidBookingDateRangeException();
        }

        this.bookingRepository.addBooking(new Booking(room, guest, checkIn, checkOut));
    }

    public Booking findBookingById(int id) {
        return this.bookingRepository.findBookingById(id);
    }

    public List<Booking> getAllBookings() {
        return this.bookingRepository.getAllBookings();
    }
}