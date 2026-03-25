import java.util.*;

/**
 * CLASS - Service (AddOnService)
 *
 * Represents an optional service
 */
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

/**
 * CLASS - AddOnServiceManager
 *
 * Manages services per reservation
 */
class AddOnServiceManager {

    // Key → Reservation ID, Value → List of services
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Calculate total service cost
    public double calculateTotalServiceCost(String reservationId) {

        double total = 0.0;

        List<Service> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

/**
 * MAIN CLASS - BOOKMYSTAY
 *
 * Use Case 7: Add-On Service Selection
 */
public class BOOKMYSTAY {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        // Example reservation ID (from previous allocation)
        String reservationId = "Single-1";

        // Create manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        Service breakfast = new Service("Breakfast", 500.0);
        Service spa = new Service("Spa", 1000.0);

        // Attach services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        // Calculate total
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        // Output
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}