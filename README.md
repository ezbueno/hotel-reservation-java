# Hotel Reservation System

A modern hotel reservation management system built with Java 25 LTS, featuring a clean architecture and console-based interface.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
- [Key Components](#key-components)
- [Notes](#notes)
- [Author](#author)
- [Contributing](#contributing)

## 🎯 Overview

The Hotel Reservation System is a comprehensive console application designed to manage hotel operations efficiently. It provides a complete solution for handling rooms, guests, and bookings with a focus on data integrity and user-friendly interactions.

## ✨ Features

### Room Management
- Add new rooms with unique identifiers
- Configure room capacity and type (Single, Double, Suite)
- List all available rooms
- Search rooms by ID
- Prevent duplicate room numbers

### Guest Management
- Register new guests with name and email
- Email validation
- List all registered guests
- Search guests by ID

### Booking Management
- Create reservations linking rooms and guests
- Date validation (YYYY-MM-DD format)
- Prevent double bookings for the same period
- Check-in and check-out date validation
- Conflict detection for overlapping reservations
- List all bookings
- Search bookings by ID

## 🏗️ Architecture

The project follows a layered architecture pattern:

```
├── entities/       # Domain models (Room, Guest, Booking)
├── repositories/   # Data access layer
├── services/       # Business logic layer
├── ui/            # User interface (console menus)
├── exceptions/    # Custom exception handling
└── app/           # Application entry point
```

### Design Patterns Used
- **Repository Pattern**: Separation of data access logic
- **Service Layer Pattern**: Business logic encapsulation
- **Record Classes**: Immutable data carriers (Java 16+)
- **Dependency Injection**: Manual DI for loose coupling

## 💻 Requirements

- **Java**: 25 LTS (**Required** for standard feature support)
- **JDK**: Version 25 (or JDK 23/24 with `--enable-preview` flag)
- **Operating System**: Windows, macOS, or Linux

> ⚠️ **Important**: This project uses `import module java.base;`, a feature introduced as preview in JDK 23 and finalized in Java 25. 
> - **Java 25+**: Works out of the box (standard feature)
> - **JDK 23/24**: Requires `--enable-preview` flag when compiling and running

## 🚀 Installation

### Option 1: Using Java 25 (Recommended)

1. **Verify Java Version**:
```bash
java -version
# Must show: java version "25" or "25.x.x"
```

2. Clone the repository:
```bash
git clone https://github.com/ezbueno/hotel-reservation-java.git
cd hotel-reservation-java
```

3. Compile the project:
```bash
javac -d bin src/developer/ezandro/**/*.java
```

4. Run the application:
```bash
java -cp bin developer.ezandro.app.HotelReservationApp
```

### Option 2: Using JDK 23/24 (Preview Feature)

If you're using JDK 23 or 24, you must enable preview features:

1. Compile with preview enabled:
```bash
javac --enable-preview --release 23 -d bin src/developer/ezandro/**/*.java
```

2. Run with preview enabled:
```bash
java --enable-preview -cp bin developer.ezandro.app.HotelReservationApp
```

> 💡 **Tip**: Download Java from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://jdk.java.net/25/).

## 📖 Usage

### Main Menu

Upon starting the application, you'll see the main menu:

```
=== Main Menu ===
1. Room Management
2. Guest Management
3. Booking Management
4. Exit
```

### Adding a Room

1. Select option `1` (Room Management)
2. Select option `1` (Add new room)
3. Enter room details:
   - Room number (e.g., "101")
   - Capacity (number of guests)
   - Room type (1: Single, 2: Double, 3: Suite)

### Registering a Guest

1. Select option `2` (Guest Management)
2. Select option `1` (Add new guest)
3. Enter guest details:
   - Full name
   - Email address (format: example@domain.com)

### Creating a Booking

1. Select option `3` (Booking Management)
2. Select option `1` (Add new booking)
3. Enter booking details:
   - Room ID (must exist)
   - Guest ID (must exist)
   - Check-in date (YYYY-MM-DD)
   - Check-out date (YYYY-MM-DD)

**Note**: The system validates that:
- Check-out date is after check-in date
- Dates are not in the past
- No booking conflicts exist for the selected room

## 📁 Project Structure

```
hotel-reservation-java/
│
├── src/
│   └── developer/
│       └── ezandro/
│           ├── app/
│           │   └── HotelReservationApp.java
│           ├── entities/
│           │   ├── Booking.java
│           │   ├── Guest.java
│           │   ├── Room.java
│           │   └── RoomType.java
│           ├── exceptions/
│           │   ├── BookingNotFoundException.java
│           │   ├── GuestNotFoundException.java
│           │   ├── InvalidBookingDateRangeException.java
│           │   ├── RoomAlreadyBookedException.java
│           │   ├── RoomAlreadyExistsException.java
│           │   ├── RoomNotFoundException.java
│           │   └── RoomTypeNotFoundException.java
│           ├── repositories/
│           │   ├── BookingRepository.java
│           │   ├── GuestRepository.java
│           │   └── RoomRepository.java
│           ├── services/
│           │   ├── BookingService.java
│           │   ├── GuestService.java
│           │   └── RoomService.java
│           └── ui/
│               ├── BookingMenu.java
│               ├── GuestMenu.java
│               ├── MainMenu.java
│               └── RoomMenu.java
│
└── README.md
```

## 🛠️ Technologies

- **Java 25 LTS**: Latest long-term support version
- **Module Import Declaration**: Using `import module java.base;` syntax
- **Java Records**: For immutable data structures
- **Java Collections Framework**: Data management
- **AtomicInteger**: Thread-safe ID generation
- **Pattern Matching**: For regex validation
- **LocalDate API**: Date handling and validation

### About Module Import Declaration

This project uses the **module import** feature, which allows importing all public APIs from an entire module with a single statement:

```java
import module java.base;
```

**What it does:**
- Imports all 54 packages exported by the `java.base` module
- Equivalent to having multiple on-demand imports like `import java.util.*;`, `import java.io.*;`, etc.
- Provides access to core Java APIs without individual import statements

**Feature Timeline:**
- **JDK 23**: Introduced as preview feature (requires `--enable-preview`)
- **JDK 24**: Available as preview feature (requires `--enable-preview`)
- **Java 25**: Finalized as standard feature (no flag needed)

**Benefits:**
- Cleaner code with fewer import statements
- Access to the entire `java.base` module API
- Simplified dependency management for core Java classes

## 🔍 Key Components

### Entities
- **Room**: Represents a hotel room with number, capacity, and type
- **Guest**: Represents a guest with personal information
- **Booking**: Links a room and guest for a specific period
- **RoomType**: Enum defining available room categories

### Validation Features
- Unique room numbers
- Email format validation
- Date format validation (YYYY-MM-DD)
- Future date validation
- Booking conflict detection
- Empty input validation

### Error Handling
- Custom exceptions for domain-specific errors
- User-friendly error messages
- Graceful error recovery
- Input validation at every step

## 📝 Notes

- All data is stored in memory (non-persistent)
- IDs are auto-generated using AtomicInteger
- The application uses a single Scanner instance shared across menus
- Date format is strictly YYYY-MM-DD
- Past dates are not allowed for bookings

## 👨‍💻 Author

**Ezandro Bueno**
- GitHub: [@ezbueno](https://github.com/ezbueno)
- Repository: [hotel-reservation-java](https://github.com/ezbueno/hotel-reservation-java)

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the issues page.

---

**Note**: This is an educational project demonstrating clean architecture principles and modern Java features, specifically the module import declaration (`import module java.base;`) introduced in JDK 23 and finalized in Java 25.