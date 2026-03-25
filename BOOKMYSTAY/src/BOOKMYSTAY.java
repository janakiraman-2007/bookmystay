import java.util.*;

/**
 * CLASS - InvalidBookingException
 *
 * Custom exception for invalid booking
 */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * CLASS - RoomInventory
 */
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public boolean isValidRoomType(String type) {
        return availability.containsKey(type);
    }
}

/**
 * CLASS - ReservationValidator
 *
 * Validates booking input
 */
class ReservationValidator {

    public void validate(String guestName,
                         String roomType,
                         RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Validate room type (case sensitive as per screenshot)
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

/**
 * CLASS - BookingRequestQueue
 */
class BookingRequestQueue {

    private Queue<String> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(String request) {
        queue.offer(request);
    }
}

/**
 * MAIN CLASS - BOOKMYSTAY
 *
 * Use Case 9: Error Handling & Validation
 */
public class BOOKMYSTAY {

    public static void main(String[] args) {

        System.out.println("Booking Validation\n");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue queue = new BookingRequestQueue();

        try {
            // Input
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            // Validate
            validator.validate(name, type, inventory);

            // If valid → add to queue
            queue.addRequest(name + " - " + type);

            System.out.println("Booking request added successfully.");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}