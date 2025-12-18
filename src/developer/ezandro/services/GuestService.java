package developer.ezandro.services;

import module java.base;
import developer.ezandro.entities.Guest;
import developer.ezandro.repositories.GuestRepository;

public record GuestService(GuestRepository guestRepository) {
    public void addGuest(String name, String email) {
        this.guestRepository.addGuest(new Guest(name, email));
    }

    public Guest findGuestById(int id) {
        return this.guestRepository.findGuestById(id);
    }

    public List<Guest> getAllGuests() {
        return this.guestRepository.getAllGuests();
    }
}