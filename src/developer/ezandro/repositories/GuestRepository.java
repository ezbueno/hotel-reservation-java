package developer.ezandro.repositories;

import module java.base;
import developer.ezandro.entities.Guest;
import developer.ezandro.exceptions.GuestNotFoundException;

public class GuestRepository {
    private final List<Guest> guests = new ArrayList<>();

    public void addGuest(Guest guest) {
        this.guests.add(guest);
    }

    public Guest findGuestById(int id) {
        return this.guests
                .stream()
                .filter(guest -> guest.id() == id)
                .findFirst()
                .orElseThrow(() -> new GuestNotFoundException(id));
    }

    public List<Guest> getAllGuests() {
        return Collections.unmodifiableList(this.guests);
    }
}