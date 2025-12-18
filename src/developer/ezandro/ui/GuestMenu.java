package developer.ezandro.ui;

import module java.base;
import developer.ezandro.entities.Guest;
import developer.ezandro.exceptions.GuestNotFoundException;
import developer.ezandro.services.GuestService;

public class GuestMenu {
    private static final String INVALID_OPTION = "ERROR: Invalid option. Please enter a number between 1 and 4.";

    private final GuestService guestService;
    private final Scanner scanner;

    public GuestMenu(GuestService guestService, Scanner scanner) {
        this.guestService = guestService;
        this.scanner = scanner;
    }

    public void handleGuestsMenu() {
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
                            this.addGuest();
                    case 2 ->
                            this.listGuests();
                    case 3 ->
                            this.findGuestById();
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
                
                === Guest Management ===
                1. Add new guest
                2. List all guests
                3. Find guest by ID
                4. Back
                
                Choose an option: \s""");
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

    private void addGuest() {
        IO.println("=== Add New Guest ===");
        String name = this.validateGuestName();
        String email = this.validateGuestEmail();
        this.guestService.addGuest(name, email);
        IO.println("\nSUCCESS: Guest added successfully.");
    }

    private void listGuests() {
        if (this.hasNoGuests()) {
            return;
        }

        this.displayListGuests();
    }

    private void findGuestById() {
        if (this.hasNoGuests()) {
            return;
        }

        this.displayGuestDetails();
    }

    private String validateGuestName() {
        while (true) {
            IO.print("Enter full name: ");
            String name = this.scanner.nextLine().trim();

            if (name.isEmpty()) {
                IO.println("ERROR: Full name must not be empty.");
                continue;
            }
            return name;
        }
    }

    private String validateGuestEmail() {
        String email = "";
        boolean hasError = true;

        while (hasError) {
            IO.print("Enter email: ");
            email = this.scanner.nextLine().trim();

            if (email.isEmpty()) {
                IO.println("ERROR: Email must not be empty.");
            } else if (!email.matches(".+@.+")) {
                IO.println("ERROR: Email must have content before and after '@'.");
            } else {
                hasError = false;
            }
        }
        return email;
    }

    private boolean hasNoGuests() {
        if (this.guestService.getAllGuests().isEmpty()) {
            IO.println("\nINFO: No guests found. Please register a guest first (option [1])");
            return true;
        }
        return false;
    }

    private void displayListGuests() {
        List<Guest> guests = this.guestService.getAllGuests();

        IO.println("\n=== List of Guests ===");
        System.out.printf("%-4s | %-20s | %-25s%n",
                "ID", "Name", "Email");
        IO.println("------------------------------------------------------");

        for (Guest guest : guests) {
            System.out.printf("%-4d | %-20s | %-25s%n",
                    guest.id(),
                    guest.name(),
                    guest.email()
            );
        }
    }

    private void displayGuestDetails() {
        Guest guest = this.validateGuestId();

        IO.println("\n=== Guest Details ===");
        System.out.printf("%-4s | %-20s | %-25s%n",
                "ID", "Name", "Email");
        IO.println("------------------------------------------------------");

        System.out.printf("%-4d | %-20s | %-25s%n",
                guest.id(),
                guest.name(),
                guest.email()
        );
    }

    private Guest validateGuestId() {
        Guest guest;

        IO.println("\n=== Find Guest by ID ===");
        while (true) {
            try {
                IO.print("Enter guest ID: ");
                String id = this.scanner.nextLine().trim();

                if (id.isEmpty()) {
                    IO.println("ERROR: Guest ID must not be empty.");
                } else {
                    int guestId = Integer.parseInt(id);

                    guest = this.guestService.findGuestById(guestId);

                    if (Objects.nonNull(guest)) {
                        break;
                    }
                }
            } catch (NumberFormatException _) {
                IO.println("ERROR: Guest ID must be a valid integer.");
            } catch (GuestNotFoundException e) {
                IO.println(e.getMessage());
            }
        }
        return guest;
    }
}