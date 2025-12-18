package developer.ezandro.ui;

import module java.base;
import developer.ezandro.entities.Booking;
import developer.ezandro.exceptions.*;
import developer.ezandro.services.BookingService;
import developer.ezandro.services.GuestService;
import developer.ezandro.services.RoomService;

public class BookingMenu {
    private static final String INVALID_OPTION = "ERROR: Invalid option. Please enter a number between 1 and 4.";

    /**
     * Regex para validar datas no formato YYYY-MM-DD.
     * - Ano: 4 dígitos (1900-2099)
     * - Mês: 01-12
     * - Dia: 01-31 (validação básica, a validação real é feita pelo LocalDate.parse)
     * <p>
     * Exemplos válidos: "2024-12-25", "2024-01-01"
     * Exemplos inválidos: "2024-13-01", "2024-02-30", "24-12-25"
     */
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

    private final BookingService bookingService;
    private final RoomService roomService;
    private final GuestService guestService;
    private final Scanner scanner;

    public BookingMenu(BookingService bookingService, RoomService roomService, GuestService guestService, Scanner scanner) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.guestService = guestService;
        this.scanner = scanner;
    }

    public void handleBookingsMenu() {
        this.start();
    }

    private void start() {
        while (true) {
            try {
                this.displaySubMenu();

                String input = this.scanner.nextLine().trim();

                if (!this.isValidOption(input)) {
                    continue;
                }

                int option = Integer.parseInt(input);

                switch (option) {
                    case 1 ->
                            this.addBooking();
                    case 2 ->
                            this.listBookings();
                    case 3 ->
                            this.findBookingById();
                    default -> {
                        return;
                    }
                }
            } catch (NumberFormatException _) {
                IO.println(INVALID_OPTION);
            }
        }
    }

    private void displaySubMenu() {
        IO.print("""
                
                === Booking Management ===
                1. Add new booking
                2. List all bookings
                3. Find booking by ID
                4. Back
                
                Choose an option:\s""");
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

    private void addBooking() {
        IO.println("=== Add New Booking ===");

        Integer roomId = this.readRoomId();
        if (roomId == null) {
            return;
        }

        Integer guestId = this.readGuestId();
        if (guestId == null) {
            return;
        }

        LocalDate checkInDate = this.readDate(
                "Enter check-in date (YYYY-MM-DD): ",
                "ERROR: Check-In Date must not be empty."
        );

        LocalDate checkOutDate = this.readDate(
                "Enter check-out date (YYYY-MM-DD): ",
                "ERROR: Check-Out Date must not be empty."
        );

        try {
            this.bookingService.createBooking(roomId, guestId, checkInDate, checkOutDate);
            IO.println("\nSUCCESS: Booking added successfully.");
        } catch (InvalidBookingDateRangeException | RoomAlreadyBookedException e) {
            IO.println(e.getMessage());
        }
    }

    private Integer readRoomId() {
        while (true) {
            try {
                IO.print("Enter room ID: ");
                String input = this.scanner.nextLine().trim();

                if (input.isEmpty()) {
                    IO.println("ERROR: Room ID must not be empty.");
                    continue;
                }

                int roomId = Integer.parseInt(input);
                return this.roomService.findRoomById(roomId).id();

            } catch (NumberFormatException e) {
                IO.println("ERROR: Room ID must be a valid integer.");
            } catch (RoomNotFoundException e) {
                IO.println(e.getMessage());
                IO.println("INFO: Please register a room first (option [1]) at the Main Menu.");
                return null;
            }
        }
    }

    private Integer readGuestId() {
        while (true) {
            try {
                IO.print("Enter Guest ID: ");
                String input = this.scanner.nextLine().trim();

                if (input.isEmpty()) {
                    IO.println("ERROR: Guest ID must not be empty.");
                    continue;
                }

                int guestId = Integer.parseInt(input);
                return this.guestService.findGuestById(guestId).id();

            } catch (NumberFormatException e) {
                IO.println("ERROR: Guest ID must be a valid integer.");
            } catch (GuestNotFoundException e) {
                IO.println(e.getMessage());
                IO.println("INFO: Please register a guest first (option [2]) at the Main Menu.");
                return null;
            }
        }
    }

    private LocalDate readDate(String prompt, String emptyError) {
        while (true) {
            IO.print(prompt);
            String input = this.scanner.nextLine().trim();

            if (input.isEmpty()) {
                IO.println(emptyError);
                continue;
            }

            if (!DATE_PATTERN.matcher(input).matches()) {
                IO.println("ERROR: Invalid date format. Please use YYYY-MM-DD.");
                continue;
            }

            try {
                LocalDate date = LocalDate.parse(input);

                if (date.isBefore(LocalDate.now())) {
                    IO.println("ERROR: Date cannot be in the past. Please enter a future date.");
                    continue;
                }

                return date;

            } catch (DateTimeParseException e) {
                IO.println("ERROR: Invalid date. Please check if the date exists.");
            }
        }
    }

    private void listBookings() {
        if (this.hasNoBookings()) {
            return;
        }

        this.displayListBookings();
    }

    private void findBookingById() {
        if (this.hasNoBookings()) {
            return;
        }

        this.displayBookingDetails();
    }

    private boolean hasNoBookings() {
        if (this.bookingService.getAllBookings().isEmpty()) {
            IO.println("\nINFO: No bookings found. Please register a booking first (option [1])");
            return true;
        }
        return false;
    }

    private void displayListBookings() {
        List<Booking> bookings = this.bookingService.getAllBookings();

        IO.println("\n=== List of Bookings ===");
        System.out.printf("%-4s | %-8s | %-15s | %-20s | %-12s | %-12s%n",
                "ID", "Room", "Room Type", "Guest", "Check-in", "Check-out");
        IO.println("---------------------------------------------------------------------------------------------");

        for (Booking booking : bookings) {
            System.out.printf("%-4s | %-8s | %-15s | %-20s | %-12s | %-12s%n",
                    booking.id(),
                    booking.room().number(),
                    booking.room().roomType(),
                    booking.guest().name(),
                    booking.checkInDate(),
                    booking.checkOutDate()
            );
        }
    }

    private void displayBookingDetails() {
        Booking booking = this.validateBookingId();

        IO.println("\n=== Booking Details ===");
        System.out.printf("%-4s | %-8s | %-15s | %-20s | %-12s | %-12s%n",
                "ID", "Room", "Room Type", "Guest", "Check-in", "Check-out");
        IO.println("---------------------------------------------------------------------------------------------");

        System.out.printf("%-4s | %-8s | %-15s | %-20s | %-12s | %-12s%n",
                booking.id(),
                booking.room().number(),
                booking.room().roomType(),
                booking.guest().name(),
                booking.checkInDate(),
                booking.checkOutDate()
        );
    }

    private Booking validateBookingId() {
        Booking booking;

        IO.println("\n=== Find Booking by ID ===");
        while (true) {
            try {
                IO.print("Enter booking ID: ");
                String id = this.scanner.nextLine().trim();

                if (id.isEmpty()) {
                    IO.println("ERROR: Booking ID must not be empty.");
                } else {
                    int bookingId = Integer.parseInt(id);

                    booking = this.bookingService.findBookingById(bookingId);

                    if (Objects.nonNull(booking)) {
                        break;
                    }
                }
            } catch (NumberFormatException _) {
                IO.println("ERROR: Booking ID must be a valid integer.");
            } catch (BookingNotFoundException e) {
                IO.println(e.getMessage());
            }
        }
        return booking;
    }
}