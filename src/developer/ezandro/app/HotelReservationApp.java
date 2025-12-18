package developer.ezandro.app;

import developer.ezandro.repositories.BookingRepository;
import developer.ezandro.repositories.GuestRepository;
import developer.ezandro.repositories.RoomRepository;
import developer.ezandro.services.BookingService;
import developer.ezandro.services.GuestService;
import developer.ezandro.services.RoomService;
import developer.ezandro.ui.MainMenu;

public class HotelReservationApp {
    private HotelReservationApp() {
    }

    static void main() {
        RoomRepository roomRepository = new RoomRepository();
        RoomService roomService = new RoomService(roomRepository);

        GuestRepository guestRepository = new GuestRepository();
        GuestService guestService = new GuestService(guestRepository);

        BookingRepository bookingRepository = new BookingRepository();
        BookingService bookingService = new BookingService(bookingRepository, roomRepository, guestRepository);

        new MainMenu(roomService, guestService, bookingService).start();
    }
}
