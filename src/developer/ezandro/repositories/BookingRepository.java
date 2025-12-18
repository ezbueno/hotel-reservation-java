package developer.ezandro.repositories;

import module java.base;
import developer.ezandro.entities.Booking;
import developer.ezandro.exceptions.BookingNotFoundException;

public class BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public Booking findBookingById(int id) {
        return this.bookings
                .stream()
                .filter(booking -> booking.id() == id)
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    public List<Booking> getAllBookings() {
        return Collections.unmodifiableList(this.bookings);
    }
}