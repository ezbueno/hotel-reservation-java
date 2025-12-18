package developer.ezandro.ui;

import module java.base;
import developer.ezandro.services.BookingService;
import developer.ezandro.services.GuestService;
import developer.ezandro.services.RoomService;

public class MainMenu {
    private static final String INVALID_OPTION = "ERROR: Invalid option. Please enter a number between 1 and 4.";

    private final RoomMenu roomMenu;
    private final GuestMenu guestMenu;
    private final BookingMenu bookingMenu;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenu(RoomService roomService, GuestService guestService, BookingService bookingService) {
        this.roomMenu = new RoomMenu(roomService, this.scanner);
        this.guestMenu = new GuestMenu(guestService, this.scanner);
        this.bookingMenu = new BookingMenu(bookingService, roomService, guestService, this.scanner);
    }

    public void start() {
        while (true) {
            try {
                this.displayMenu();

                String input = this.scanner.nextLine().trim();

                if (!this.isValidOption(input)) {
                    continue;
                }

                int option = Integer.parseInt(input);

                switch (option) {
                    case 1 ->
                            this.roomMenu.handleRoomsMenu();
                    case 2 ->
                            this.guestMenu.handleGuestsMenu();
                    case 3 ->
                            this.bookingMenu.handleBookingsMenu();
                    default -> {
                        this.exit();
                        return;
                    }
                }

            } catch (NumberFormatException _) {
                IO.println(INVALID_OPTION);
            }
        }
    }

    private void displayMenu() {
        IO.print("""
                
                === Main Menu ===
                1. Room Management
                2. Guest Management
                3. Booking Management
                4. Exit
                
                Choose an option:\s""");
    }

    private void exit() {
        IO.println("""
                
                === Program terminated ===
                Thank you for using Hotel Reservation System.
                """);
    }

    private boolean isValidOption(String input) {
        if (input.isEmpty()) {
            IO.println("ERROR: Option cannot be empty. Please enter a number between 1 and 4.");
            return false;
        }

        try {
            int option = Integer.parseInt(input);

            if (option < 1 || option > 4) {
                IO.println(INVALID_OPTION);
                return false;
            }

            return true;

        } catch (NumberFormatException _) {
            IO.println(INVALID_OPTION);
            return false;
        }
    }
}