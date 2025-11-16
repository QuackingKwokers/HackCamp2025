import java.util.*;
import java.time.LocalDate;

public class Pantry {
    private List<Food> foods;
    private String pantryName;
    
    // Constructor
    public Pantry(String pantryName) {
        this.pantryName = pantryName;
        this.foods = new ArrayList<>();
    }
    
    // Add food to pantry
    public void addFood(Food food) {
        foods.add(food);
        System.out.println("✓ Added: " + food.getName());
    }
    
    // Add food with parameters
    public void addFood(String name, int quantity, String unit, LocalDate expiryDate, String category) {
        addFood(new Food(name, quantity, unit, expiryDate, category));
    }
    
    // Remove food by name
    public boolean removeFood(String name) {
        return foods.removeIf(food -> food.getName().equalsIgnoreCase(name));
    }
    
    // Get all foods
    public List<Food> getAllFoods() {
        return new ArrayList<>(foods);
    }
    
    // Get expired foods
    public List<Food> getExpiredFoods() {
        List<Food> expired = new ArrayList<>();
        for (Food food : foods) {
            if (food.isExpired()) {
                expired.add(food);
            }
        }
        // Sort by most expired first
        expired.sort((a, b) -> Long.compare(b.getDaysUntilExpiry(), a.getDaysUntilExpiry()));
        return expired;
    }
    
    // Get expiring soon (within 3 days)
    public List<Food> getExpiringSoon() {
        List<Food> soon = new ArrayList<>();
        for (Food food : foods) {
            if (food.isExpiringSoon() && !food.isExpired()) {
                soon.add(food);
            }
        }
        // Sort by soonest first
        soon.sort((a, b) -> Long.compare(a.getDaysUntilExpiry(), b.getDaysUntilExpiry()));
        return soon;
    }
    
    // Get good foods (not expiring soon)
    public List<Food> getGoodFoods() {
        List<Food> good = new ArrayList<>();
        for (Food food : foods) {
            if (!food.isExpired() && !food.isExpiringSoon()) {
                good.add(food);
            }
        }
        // Sort by soonest expiry
        good.sort((a, b) -> Long.compare(a.getDaysUntilExpiry(), b.getDaysUntilExpiry()));
        return good;
    }
    
    // Get foods by category
    public List<Food> getFoodsByCategory(String category) {
        List<Food> result = new ArrayList<>();
        for (Food food : foods) {
            if (food.getCategory().equalsIgnoreCase(category)) {
                result.add(food);
            }
        }
        return result;
    }
    
    // Search foods by name
    public List<Food> searchFood(String name) {
        List<Food> result = new ArrayList<>();
        for (Food food : foods) {
            if (food.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(food);
            }
        }
        return result;
    }
    
    // Calculate total waste (expired items)
    public int getTotalExpiredCount() {
        return getExpiredFoods().size();
    }
    
    // Calculate expiring this week
    public int getExpiringThisWeekCount() {
        int count = 0;
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        for (Food food : foods) {
            if (food.getExpiryDate().isBefore(nextWeek) && !food.isExpired()) {
                count++;
            }
        }
        return count;
    }
    
    // Get pantry statistics
    public void printStats() {
        System.out.println("\n📊 PANTRY STATISTICS");
        System.out.println("==================");
        System.out.println("Total Items: " + foods.size());
        System.out.println("Expired: " + getTotalExpiredCount());
        System.out.println("Expiring This Week: " + getExpiringThisWeekCount());
        System.out.println("Expiring Soon (≤3 days): " + getExpiringSoon().size());
        System.out.println("Good to Use: " + getGoodFoods().size());
        System.out.println("==================\n");
    }
    
    // Print all foods organized by status
    public void printAllFoods() {
        System.out.println("\n🍎 " + pantryName.toUpperCase());
        System.out.println("================================\n");
        
        // Expired
        List<Food> expired = getExpiredFoods();
        if (!expired.isEmpty()) {
            System.out.println("🚨 EXPIRED:");
            for (Food food : expired) {
                System.out.println("  " + food);
            }
            System.out.println();
        }
        
        // Expiring Soon
        List<Food> soon = getExpiringSoon();
        if (!soon.isEmpty()) {
            System.out.println("⏰ EXPIRING SOON (≤3 days):");
            for (Food food : soon) {
                System.out.println("  " + food);
            }
            System.out.println();
        }
        
        // Good
        List<Food> good = getGoodFoods();
        if (!good.isEmpty()) {
            System.out.println("✅ GOOD TO USE:");
            for (Food food : good) {
                System.out.println("  " + food);
            }
            System.out.println();
        }
        
        if (foods.isEmpty()) {
            System.out.println("No foods in pantry.\n");
        }
    }
    
    // Get pantry name
    public String getPantryName() {
        return pantryName;
    }
}
