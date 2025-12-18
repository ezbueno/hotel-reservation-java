package developer.ezandro.entities;

import module java.base;

public record Guest(
        int id,
        String name,
        String email) {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    public Guest(String name, String email) {
        this(ID_GENERATOR.getAndIncrement(), name, email);
    }
}