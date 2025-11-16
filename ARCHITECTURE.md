# 🍎 Food Tracker GUI - Architecture & Implementation Guide

## Project Overview

Your Food Tracker has been successfully converted from a console-based application to a professional Java Swing GUI. The code maintains the original data models and business logic while adding a modern graphical interface.

---

## Architecture

### Model-View Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    FoodTrackerGUI (View)                 │
│        Handles all user interface interactions            │
└─────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────┐
│  Pantry (Business Logic) + Food (Data Model)            │
│    - Manages food inventory                              │
│    - Calculates expiry information                       │
│    - Filters and searches foods                          │
└─────────────────────────────────────────────────────────┘
```

### Class Diagram

```
Food
├── name: String
├── quantity: int
├── unit: String
├── expiryDate: LocalDate
├── addedDate: LocalDate
├── category: String
├── getDaysUntilExpiry(): long
├── isExpired(): boolean
├── isExpiringSoon(): boolean
└── getStatus(): String

Pantry
├── foods: List<Food>
├── pantryName: String
├── addFood(...)
├── removeFood(name)
├── getAllFoods(): List<Food>
├── getExpiredFoods(): List<Food>
├── getExpiringSoon(): List<Food>
├── getGoodFoods(): List<Food>
├── getFoodsByCategory(category): List<Food>
├── searchFood(name): List<Food>
└── Statistics methods

FoodTrackerGUI extends JFrame
├── mainPanel: JPanel
├── foodTable: JTable
├── pantry: Pantry
├── initializeUI()
├── createTopPanel()
├── createCenterPanel()
├── createBottomPanel()
├── showAddFoodDialog()
├── updateTableData()
└── Various action handlers
```

---

## Key Components

### 1. Food.java (Data Model)
**Responsibility**: Represents a single food item

**Key Methods**:
- `getDaysUntilExpiry()`: Calculates days until expiry using ChronoUnit
- `isExpired()`: Checks if product is past expiry
- `isExpiringSoon()`: Checks if expiring within 3 days
- `getStatus()`: Returns "EXPIRED", "EXPIRING SOON", or "GOOD"

**Data Stored**:
- Item details (name, quantity, unit, category)
- Temporal info (added date, expiry date)
- Calculated properties (days left, status)

### 2. Pantry.java (Business Logic)
**Responsibility**: Manages the collection of foods

**Key Methods**:
- `addFood()`: Adds food to the pantry
- `removeFood()`: Removes food by name
- `getExpiredFoods()`: Returns all expired items (sorted)
- `getExpiringSoon()`: Returns items expiring within 3 days
- `getGoodFoods()`: Returns fresh items
- `getFoodsByCategory()`: Filters by category
- `searchFood()`: Searches by name (partial match)

**Features**:
- Maintains ArrayList of Food objects
- Provides multiple query methods
- Handles sorting for display purposes

### 3. FoodTrackerGUI.java (View/Controller)
**Responsibility**: Entire GUI implementation

**Key Components**:

#### Constructor
```java
public FoodTrackerGUI()
- Initializes Pantry instance
- Sets up JFrame properties (size, location, title)
- Calls initializeUI()
```

#### UI Panels

1. **Top Panel** (createTopPanel)
   - Title label with emoji
   - Real-time statistics display
   - Filter dropdown

2. **Center Panel** (createCenterPanel)
   - JTable with food data
   - Custom cell renderer for color coding
   - Scrollable with 7 columns:
     - Name, Quantity, Unit, Category, Expiry Date, Days Left, Status

3. **Bottom Panel** (createBottomPanel)
   - Action buttons:
     - ➕ Add Food
     - 📋 View Details
     - ❌ Remove
     - 📦 Load Sample Data
     - 🔄 Refresh

#### Dialog Windows

**Add Food Dialog** (showAddFoodDialog)
```
- TextField: Food Name
- TextField: Quantity (validated as integer)
- ComboBox: Unit (pcs, kg, L, g, ml)
- ComboBox: Category
- TextField: Expiry Date (yyyy-MM-dd)
- Buttons: Add, Cancel
- Error handling with JOptionPane
```

#### Key Features

**Dynamic Table Filtering**
```java
switch (filter) {
    case "Expired" → pantry.getExpiredFoods()
    case "Expiring Soon" → pantry.getExpiringSoon()
    case "Good to Use" → pantry.getGoodFoods()
    case "[Category]" → pantry.getFoodsByCategory(category)
    case "All" → pantry.getAllFoods()
}
```

**Status Color Coding**
```
StatusCellRenderer renders:
- Red (RGB: 255, 200, 200) = EXPIRED
- Yellow (RGB: 255, 255, 200) = EXPIRING SOON
- Green (RGB: 200, 255, 200) = GOOD
```

**Real-time Statistics**
```
updateStatsLabel() displays:
- Total items
- Expired count
- Expiring soon count
- Good items count
```

---

## Data Flow

### Adding Food
```
User clicks "Add Food"
    ↓
showAddFoodDialog() displays JDialog
    ↓
User fills form and clicks "Add"
    ↓
Input validation (name not empty, quantity > 0, valid date)
    ↓
pantry.addFood(name, quantity, unit, expiryDate, category)
    ↓
Pantry creates Food object and adds to ArrayList
    ↓
updateTableData() refreshes JTable
    ↓
updateStatsLabel() updates statistics
    ↓
Success message displayed
```

### Filtering Foods
```
User selects filter from dropdown
    ↓
Dropdown change event triggered
    ↓
updateTableData() called
    ↓
Filter value read from dropdown
    ↓
Appropriate Pantry method called (getExpiredFoods, etc.)
    ↓
List<Food> returned from Pantry
    ↓
Each Food converted to table row [Name, Qty, Unit, Cat, Date, Days, Status]
    ↓
Table model updated with setRowCount(0) then addRow()
    ↓
JTable redrawn with current data
```

### Removing Food
```
User selects row in table
    ↓
User clicks "Remove"
    ↓
Confirmation dialog shown
    ↓
User confirms → pantry.removeFood(name)
    ↓
Pantry uses removeIf() to remove matching Food
    ↓
updateTableData() refreshes view
    ↓
updateStatsLabel() updates statistics
```

---

## GUI Layout

```
╔═══════════════════════════════════════════════════════╗
║                   Top Panel (BorderLayout NORTH)      ║
║  Title & Stats  |  Filter Dropdown                    ║
║                                                       ║
║═══════════════════════════════════════════════════════║
║                                                       ║
║  Center Panel (BorderLayout CENTER)                  ║
║                                                       ║
║  ┌─────────────────────────────────────────────┐    ║
║  │ Name|Qty|Unit|Category|Expiry Date|Days|St │    ║
║  │ ──────────────────────────────────────────  │    ║
║  │ Milk│ 1 │ L  │ Dairy  │ 2024-12-20 │ 2  │SOON│ ║
║  │ Bread│1 │pcs │Pantry │ 2024-12-21 │ 1  │SOON│ ║
║  │ Tomato│3│pcs│Produce│2024-12-19 │ -1 │EXP  │ ║
║  │ ...                                         │    ║
║  └─────────────────────────────────────────────┘    ║
║                                                       ║
║═══════════════════════════════════════════════════════║
║     Bottom Panel (FlowLayout)                         ║
║  [➕ Add] [📋 Details] [❌ Remove] [📦 Sample] [🔄 Refresh] ║
╚═══════════════════════════════════════════════════════╝
```

---

## Design Patterns Used

### 1. **Model-View Separation**
   - Model: Food, Pantry
   - View: FoodTrackerGUI panels and table
   - Clean separation of concerns

### 2. **Observer Pattern (via JTable)**
   - Table model updates trigger automatic GUI refresh
   - DefaultTableModel manages data and view synchronization

### 3. **Builder Pattern (UI Construction)**
   - createTopPanel(), createCenterPanel(), createBottomPanel()
   - Each method builds and returns a complete panel

### 4. **Strategy Pattern (Filtering)**
   - Different filtering strategies implemented via switch statement
   - Easy to add new filters

### 5. **Custom Cell Renderer**
   - StatusCellRenderer customizes cell appearance
   - Color coding without modifying underlying data

---

## Important Implementation Details

### Date Handling
```java
DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
LocalDate expiryDate = LocalDate.parse(input, dateFormatter);
```

### Thread Safety
```java
SwingUtilities.invokeLater(() -> {
    FoodTrackerGUI frame = new FoodTrackerGUI();
    frame.setVisible(true);
});
```
Ensures GUI operations happen on the Event Dispatch Thread.

### Table Configuration
```java
foodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
foodTable.setRowHeight(25);
// Column widths set proportionally
// Cell renderer applied to status column
```

### Input Validation
```java
if (name.isEmpty()) { ... } // Check name not empty
Integer.parseInt(...) // Throws exception if not int
LocalDate.parse(...) // Throws exception if invalid date
```

---

## Extending the Application

### Add New Filter Category
```java
// In updateTableData() switch statement:
case "Frozen":
    foods = pantry.getFoodsByCategory("frozen");
    break;
```

### Add New Food Property
```java
// In Food.java add field:
private String location; // e.g., "fridge", "pantry"

// In FoodTrackerGUI, add table column:
tableModel.addColumn("Location");
// Add location to row data in updateTableData()
```

### Add Statistics View
```java
// Create new dialog with statistics
// Use pantry methods:
pantry.getTotalExpiredCount()
pantry.getExpiringThisWeekCount()
pantry.getGoodFoods().size()
```

### Add Data Persistence
```java
// Implement JSON serialization:
// - Save: Convert foods to JSON file
// - Load: Parse JSON file to Food objects
// - Update constructor to load on startup
```

---

## Compilation & Execution

### Compile
```bash
javac Food.java Pantry.java FoodTrackerGUI.java
```

### Run
```bash
java FoodTrackerGUI
```

### Requirements
- Java 11+ (uses LocalDate API)
- No external dependencies
- Cross-platform (works on Windows, Mac, Linux)

---

## Performance Considerations

- **Table Updates**: O(n) where n = number of foods (acceptable for typical pantry size)
- **Searches**: O(n) with substring matching (efficient for small datasets)
- **Sorting**: Built into getExpiredFoods() and getExpiringSoon()
- **Memory**: All data kept in memory (fine for typical household pantry)

---

## Future Enhancement Ideas

1. **Data Persistence**: Save/load from JSON file
2. **Advanced Search**: Regex patterns, date range queries
3. **Charts**: Pie charts showing food distribution by category
4. **Notifications**: Desktop alerts for expiring items
5. **Multiple Pantries**: Switch between different pantry locations
6. **Barcode Scanning**: Quick item entry
7. **Meal Planning**: Suggest recipes based on available ingredients
8. **Export**: Generate reports (PDF, CSV)
9. **Dark Mode**: Alternative UI theme
10. **Undo/Redo**: Transaction history

---

## Glossary

| Term | Definition |
|------|-----------|
| JFrame | Main application window |
| JPanel | Container for grouping components |
| JTable | Grid display for tabular data |
| DefaultTableModel | Data model for JTable |
| JDialog | Modal dialog window |
| JComboBox | Dropdown selection component |
| JOptionPane | Dialog for alerts and confirmations |
| Event Dispatch Thread | Swing's single thread for GUI updates |
| Cell Renderer | Customizes cell appearance in tables |

---

**Congratulations! Your Food Tracker now has a professional GUI! 🎉**
