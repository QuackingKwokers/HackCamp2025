import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Food {
    private String name;
    private int quantity;
    private String unit;
    private LocalDate expiryDate;
    private LocalDate addedDate;
    private String category;
    
    // Constructor
    public Food(String name, int quantity, String unit, LocalDate expiryDate, String category) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
        this.addedDate = LocalDate.now();
        this.category = category;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public LocalDate getAddedDate() {
        return addedDate;
    }
    
    public String getCategory() {
        return category;
    }
    
    // Calculate days until expiry
    public long getDaysUntilExpiry() {
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }
    
    // Check if expired
    public boolean isExpired() {
        return getDaysUntilExpiry() < 0;
    }
    
    // Check if expiring soon (within 3 days)
    public boolean isExpiringSoon() {
        long daysLeft = getDaysUntilExpiry();
        return daysLeft >= 0 && daysLeft <= 3;
    }
    
    // Get status
    public String getStatus() {
        long daysLeft = getDaysUntilExpiry();
        
        if (daysLeft < 0) {
            return "EXPIRED";
        } else if (daysLeft <= 3) {
            return "EXPIRING SOON";
        } else {
            return "GOOD";
        }
    }
    
    // Get color for console display
    public String getStatusColor() {
        if (isExpired()) {
            return "\u001B[31m"; // Red
        } else if (isExpiringSoon()) {
            return "\u001B[33m"; // Yellow
        } else {
            return "\u001B[32m"; // Green
        }
    }
    
    // Reset color
    private static final String RESET = "\u001B[0m";
    
    // String representation
    @Override
    public String toString() {
        long daysLeft = getDaysUntilExpiry();
        String daysText = isExpired() ? 
            "Expired " + Math.abs(daysLeft) + " days ago" :
            "Expires in " + daysLeft + " days";
        
        return getStatusColor() + 
            String.format("%-20s | %d %s | %-10s | %s | %s", 
                name, quantity, unit, category, expiryDate, daysText) + 
            RESET;
    }
    
    // Detailed view
    public String getDetails() {
        return String.format(
            "Food: %s\n" +
            "Quantity: %d %s\n" +
            "Category: %s\n" +
            "Added: %s\n" +
            "Expires: %s\n" +
            "Days Left: %d\n" +
            "Status: %s",
            name, quantity, unit, category, addedDate, expiryDate, 
            getDaysUntilExpiry(), getStatus()
        );
    }
}
