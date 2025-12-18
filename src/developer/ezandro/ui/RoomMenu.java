package developer.ezandro.ui;

import module java.base;
import developer.ezandro.entities.Room;
import developer.ezandro.entities.RoomType;
import developer.ezandro.exceptions.RoomAlreadyExistsException;
import developer.ezandro.exceptions.RoomNotFoundException;
import developer.ezandro.exceptions.RoomTypeNotFoundException;
import developer.ezandro.services.RoomService;

public record RoomMenu(RoomService roomService, Scanner scanner) {
    private static final String INVALID_OPTION = "ERROR: Invalid option. Please enter a number between 1 and 4.";

    public void handleRoomsMenu() {
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
                            this.addRoom();
                    case 2 ->
                            this.listRooms();
                    case 3 ->
                            this.findRoomById();
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
                
                === Room Management ===
                1. Add new room
                2. List all rooms
                3. Find room by ID
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

    private void addRoom() {
        IO.println("=== Add New Room ===");
        String number = this.validateRoomNumber();
        int capacity = this.validateRoomCapacity();
        RoomType roomType = this.validateRoomType();
        this.roomService.addRoom(number, capacity, roomType);
        IO.println("\nSUCCESS: Room added successfully.");
    }

    private void listRooms() {
        if (this.hasNoRooms()) {
            return;
        }

        this.displayListRooms();
    }

    private void findRoomById() {
        if (this.hasNoRooms()) {
            return;
        }

        this.displayRoomDetails();
    }

    private String validateRoomNumber() {
        IO.print("Enter room number: ");
        while (true) {
            try {
                String number = this.scanner.nextLine().trim();

                if (number.isEmpty()) {
                    IO.println("ERROR: Room number must not be empty.");
                    continue;
                }

                this.roomService.validateRoomNumberAvailable(number);
                return number;

            } catch (RoomAlreadyExistsException e) {
                IO.println(e.getMessage());
                IO.print("Enter a different room number: ");
            }
        }
    }

    private int validateRoomCapacity() {
        int capacity;

        while (true) {
            try {
                IO.print("Enter room capacity: ");
                String inputCapacity = this.scanner.nextLine().trim();

                if (inputCapacity.isEmpty()) {
                    IO.println("ERROR: Room capacity must not be empty.");
                } else {
                    capacity = Integer.parseInt(inputCapacity);
                    return capacity;
                }

            } catch (NumberFormatException _) {
                IO.println("ERROR: Capacity must be a valid integer.");
            }
        }
    }

    private RoomType validateRoomType() {
        int count = 1;

        IO.println("Select room type:");
        for (RoomType roomType : RoomType.values()) {
            IO.println(count + ". " + roomType);
            count++;
        }

        String inputRoomType = "";

        while (true) {
            try {
                IO.print("Choose an option: ");
                inputRoomType = this.scanner.nextLine().trim();

                if (inputRoomType.isEmpty()) {
                    IO.println("ERROR: Room type must not be empty.");
                } else {
                    int optionNumber = Integer.parseInt(inputRoomType);
                    return RoomType.fromOptionNumber(optionNumber);
                }

            } catch (NumberFormatException _) {
                IO.println(String.format("ERROR: Invalid room type number. " +
                        "Please choose from 1 to %d.", RoomType.values().length));
            } catch (RoomTypeNotFoundException e) {
                IO.println(e.getMessage());
            }
        }
    }

    private boolean hasNoRooms() {
        if (this.roomService.getAllRooms().isEmpty()) {
            IO.println("\nINFO: No rooms found. Please register a room first (option [1])");
            return true;
        }
        return false;
    }

    private void displayListRooms() {
        List<Room> rooms = this.roomService.getAllRooms();

        IO.println("\n=== List of Rooms ===");
        System.out.printf("%-4s | %-6s | %-8s | %-10s%n",
                "ID", "Room", "Capacity", "Type");
        IO.println("------------------------------------");

        for (Room room : rooms) {
            System.out.printf("%-4d | %-6s | %-8d | %-10s%n",
                    room.id(),
                    room.number(),
                    room.capacity(),
                    room.roomType()
            );
        }
    }

    private void displayRoomDetails() {
        Room room = this.validateRoomId();

        IO.println("\n=== Room Details ===");
        System.out.printf("%-4s | %-6s | %-8s | %-10s%n",
                "ID", "Room", "Capacity", "Type");
        IO.println("------------------------------------");

        System.out.printf("%-4d | %-6s | %-8d | %-10s%n",
                room.id(),
                room.number(),
                room.capacity(),
                room.roomType()
        );
    }

    private Room validateRoomId() {
        Room room;

        IO.println("\n=== Find Room by ID ===");
        while (true) {
            try {
                IO.print("Enter room ID: ");
                String id = this.scanner.nextLine().trim();

                if (id.isEmpty()) {
                    IO.println("ERROR: Room ID must not be empty.");
                } else {
                    int roomId = Integer.parseInt(id);

                    room = this.roomService.findRoomById(roomId);

                    if (Objects.nonNull(room)) {
                        break;
                    }
                }
            } catch (NumberFormatException _) {
                IO.println("ERROR: Room ID must be a valid integer.");
            } catch (RoomNotFoundException e) {
                IO.println(e.getMessage());
            }
        }
        return room;
    }
}