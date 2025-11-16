import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class FoodTrackerApp {
    private Pantry pantry;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;
    
    // Constructor
    public FoodTrackerApp() {
        this.pantry = new Pantry("My Kitchen");
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    // Main menu
    public void start() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   🍎 FOOD EXPIRY TRACKER - JAVA 🍎    ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add Food");
            System.out.println("2. View All Foods");
            System.out.println("3. View Expired Foods");
            System.out.println("4. View Expiring Soon");
            System.out.println("5. Search Food");
            System.out.println("6. Remove Food");
            System.out.println("7. View Statistics");
            System.out.println("8. Add Sample Data");
            System.out.println("9. Exit");
            System.out.print("\nChoose option (1-9): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    addFood();
                    break;
                case "2":
                    pantry.printAllFoods();
                    break;
                case "3":
                    viewExpired();
                    break;
                case "4":
                    viewExpiringSoon();
                    break;
                case "5":
                    searchFood();
                    break;
                case "6":
                    removeFood();
                    break;
                case "7":
                    pantry.printStats();
                    break;
                case "8":
                    addSampleData();
                    break;
                case "9":
                    System.out.println("\nGoodbye! 👋\n");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    
    // Add food
    private void addFood() {
        System.out.println("\n--- ADD FOOD ---");
        
        System.out.print("Food name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        
        System.out.print("Quantity: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }
        
        System.out.print("Unit (pcs/kg/L/g/ml): ");
        String unit = scanner.nextLine().trim();
        if (unit.isEmpty()) unit = "pcs";
        
        System.out.print("Expiry date (yyyy-MM-dd): ");
        LocalDate expiryDate;
        try {
            expiryDate = LocalDate.parse(scanner.nextLine().trim(), dateFormatter);
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }
        
        System.out.print("Category (produce/dairy/meat/pantry/frozen/other): ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) category = "other";
        
        pantry.addFood(name, quantity, unit, expiryDate, category);
    }
    
    // View expired foods
    private void viewExpired() {
        System.out.println("\n🚨 EXPIRED FOODS");
        System.out.println("================");
        List<Food> expired = pantry.getExpiredFoods();
        
        if (expired.isEmpty()) {
            System.out.println("No expired foods. Great! ✓");
        } else {
            for (Food food : expired) {
                System.out.println(food);
            }
        }
    }
    
    // View expiring soon
    private void viewExpiringSoon() {
        System.out.println("\n⏰ EXPIRING SOON (≤3 days)");
        System.out.println("==========================");
        List<Food> soon = pantry.getExpiringSoon();
        
        if (soon.isEmpty()) {
            System.out.println("No foods expiring soon.");
        } else {
            for (Food food : soon) {
                System.out.println(food);
            }
        }
    }
    
    // Search food
    private void searchFood() {
        System.out.println("\n--- SEARCH FOOD ---");
        System.out.print("Search term: ");
        String term = scanner.nextLine().trim();
        
        List<Food> results = pantry.searchFood(term);
        
        if (results.isEmpty()) {
            System.out.println("No foods found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Food food : results) {
                System.out.println(food);
            }
        }
    }
    
    // Remove food
    private void removeFood() {
        System.out.println("\n--- REMOVE FOOD ---");
        System.out.print("Food name to remove: ");
        String name = scanner.nextLine().trim();
        
        if (pantry.removeFood(name)) {
            System.out.println("✓ Removed: " + name);
        } else {
            System.out.println("Food not found.");
        }
    }
    
    // Add sample data
    private void addSampleData() {
        System.out.println("\nAdding sample data...");
        
        LocalDate today = LocalDate.now();
        
        pantry.addFood("Milk", 1, "L", today.plusDays(2), "dairy");
        pantry.addFood("Bread", 1, "pcs", today.plusDays(1), "pantry");
        pantry.addFood("Tomato", 3, "pcs", today.minusDays(1), "produce");
        pantry.addFood("Chicken", 500, "g", today.plusDays(5), "meat");
        pantry.addFood("Yogurt", 2, "pcs", today.plusDays(3), "dairy");
        pantry.addFood("Lettuce", 1, "pcs", today.plusDays(2), "produce");
        pantry.addFood("Ice Cream", 1, "L", today.plusDays(30), "frozen");
        
        System.out.println("✓ Sample data added!\n");
    }
    
    // Main method
    public static void main(String[] args) {
        FoodTrackerApp app = new FoodTrackerApp();
        app.start();
    }
}
