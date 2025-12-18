package developer.ezandro.entities;

import developer.ezandro.exceptions.RoomTypeNotFoundException;

public enum RoomType {
    SINGLE(1),
    DOUBLE(2),
    SUITE(3);

    private final int optionNumber;

    RoomType(int optionNumber) {
        this.optionNumber = optionNumber;
    }

    public int getOptionNumber() {
        return this.optionNumber;
    }

    public static RoomType fromOptionNumber(int optionNumber) {
        for (RoomType roomType : RoomType.values()) {
            if (roomType.getOptionNumber() == optionNumber) {
                return roomType;
            }
        }
        throw new RoomTypeNotFoundException(optionNumber);
    }
}