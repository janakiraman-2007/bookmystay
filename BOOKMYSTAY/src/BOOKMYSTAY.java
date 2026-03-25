import java.util.*;

/**
 * CLASS - Reservation
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * CLASS - BookingRequestQueue (FIFO)
 */
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

/**
 * CLASS - RoomInventory
 */
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 1);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        availability.put(type, getAvailable(type) - 1);
    }
}

/**
 * CLASS - RoomAllocationService
 *
 * Use Case 6: Reservation Confirmation & Allocation
 */
class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        if (inventory.getAvailable(type) > 0) {

            String roomId = generateRoomId(type);

            allocatedRoomIds.add(roomId);

            assignedRoomsByType
                    .computeIfAbsent(type, k -> new HashSet<>())
                    .add(roomId);

            inventory.reduceRoom(type);

            System.out.println("Booking confirmed for Guest: "
                    + reservation.getGuestName()
                    + ", Room ID: " + roomId);

        } else {
            System.out.println("No rooms available for "
                    + reservation.getGuestName()
                    + " (" + type + ")");
        }
    }

    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }
}

/**
 * MAIN CLASS - BOOKMYSTAY
 *
 * Use Case 6
 */
public class BOOKMYSTAY {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Double"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Allocation service
        RoomAllocationService service = new RoomAllocationService();

        // Process FIFO
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            service.allocateRoom(r, inventory);
        }
    }
}