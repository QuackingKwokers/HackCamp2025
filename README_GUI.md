# 🍎 Food Expiry Tracker - GUI Version

A professional Java Swing GUI application for tracking food expiration dates and managing your kitchen pantry.

## Features

### Core Features
- ✅ **Add Foods**: Easily add food items with quantity, unit, category, and expiry date
- 📋 **View All Foods**: See your complete pantry inventory
- 🚨 **Expired Foods**: Track items that have already expired
- ⏰ **Expiring Soon**: Get alerts for items expiring within 3 days
- 🔍 **Search & Filter**: Quick search by name or filter by status/category
- ❌ **Remove Foods**: Delete items from your pantry
- 📊 **Statistics**: View overall pantry statistics at a glance
- 📦 **Sample Data**: Load demo data for testing

### GUI Features
- 🎨 **Clean Interface**: Modern Java Swing interface with intuitive layout
- 📊 **Color-Coded Status**: Visual indicators for food status (Red=Expired, Yellow=Expiring Soon, Green=Good)
- 🎯 **Filtering System**: Filter by:
  - All items
  - Expired
  - Expiring Soon
  - Good to Use
  - By Category (Dairy, Produce, Meat, Pantry, Frozen, Other)
- 📈 **Live Statistics**: Real-time stats showing total, expired, expiring soon, and good items
- 💾 **Persistent Data**: Works with the data storage capabilities of the console app

## Installation

### Requirements
- Java 11 or higher
- No external dependencies (uses only built-in Swing library)

### Compilation

**On Linux/Mac:**
```bash
cd food-tracker-java
chmod +x run_gui.sh
./run_gui.sh
```

**On Windows:**
```bash
cd food-tracker-java
javac Food.java Pantry.java FoodTrackerGUI.java
java FoodTrackerGUI
```

**Manual Compilation (All Platforms):**
```bash
cd food-tracker-java
javac Food.java Pantry.java FoodTrackerGUI.java
java FoodTrackerGUI
```

## Usage Guide

### Adding Food Items
1. Click the **➕ Add Food** button
2. Fill in the following details:
   - **Food Name**: Name of the food item
   - **Quantity**: Number of units
   - **Unit**: pcs (pieces), kg, L (liters), g (grams), ml (milliliters)
   - **Category**: Dairy, Produce, Meat, Pantry, Frozen, or Other
   - **Expiry Date**: Date in format yyyy-MM-dd (e.g., 2024-12-25)
3. Click **Add** to save the item

### Viewing Food Items
- The main table displays all foods organized by expiry status
- Each row shows: Name, Quantity, Unit, Category, Expiry Date, Days Left, and Status
- Use the **Filter** dropdown to view specific categories or statuses

### Removing Food Items
1. Select a food item from the table by clicking on its row
2. Click the **❌ Remove** button
3. Confirm the deletion in the dialog box

### Viewing Detailed Information
1. Select a food item from the table
2. Click **📋 View Details** to see:
   - Complete food information
   - Days until expiration
   - Current status
   - Category and quantity details

### Loading Sample Data
Click **📦 Load Sample Data** to populate the pantry with example items for testing. This includes items with various expiration statuses.

### Refreshing the Display
Click **🔄 Refresh** to update the display with the latest data.

## Status Indicators

- 🟢 **GOOD** (Green): Food is fresh and not expiring soon
- 🟡 **EXPIRING SOON** (Yellow): Food expires within 3 days
- 🔴 **EXPIRED** (Red): Food has already expired

## Filtering Options

| Filter | Shows |
|--------|-------|
| All | All items in the pantry |
| Expired | Items that have passed their expiry date |
| Expiring Soon | Items expiring within 3 days |
| Good to Use | Fresh items with more than 3 days remaining |
| Dairy | Milk, yogurt, cheese, etc. |
| Produce | Vegetables, fruits, etc. |
| Meat | Chicken, beef, fish, etc. |
| Pantry | Canned goods, grains, etc. |
| Frozen | Frozen items |
| Other | Miscellaneous items |

## Keyboard Shortcuts
- **Ctrl+Q**: Exit the application (Windows/Linux)

## Data Storage

The GUI version uses the same `Pantry` class as the console version, allowing you to easily switch between console and GUI interfaces while maintaining data compatibility.

### Console Version Commands
If you prefer the console version, use:
```bash
javac Food.java Pantry.java FoodTrackerApp.java
java FoodTrackerApp
```

## Project Structure

```
food-tracker-java/
├── Food.java                 # Food item model class
├── Pantry.java              # Pantry management class
├── FoodTrackerApp.java      # Console-based application
├── FoodTrackerGUI.java      # Swing GUI application
├── run_gui.sh               # Script to compile and run GUI
├── run.sh                   # Script to run console version
├── run.bat                  # Batch file to run console version
└── README.md                # This file
```

## Troubleshooting

### "javac: command not found"
- Install Java Development Kit (JDK) if not already installed
- Add Java to your system PATH

### Date Format Error
- Ensure date is in format: yyyy-MM-dd (e.g., 2024-12-25)
- 4-digit year, 2-digit month, 2-digit day

### GUI doesn't appear
- Ensure Java is properly installed with Swing support
- Try running from command line to see error messages
- Check that no other Java applications are blocking the port

## Future Enhancements

Potential features for future versions:
- 💾 JSON data persistence to save pantry between sessions
- 📈 Advanced statistics and charts (pie charts, usage trends)
- 🔔 Notifications and reminders
- 🗂️ Multiple pantries management
- 🔍 Advanced search with filters
- 📱 Mobile app version
- ☁️ Cloud sync for family members

## License

Educational project for learning Java and GUI development.

## Support

For issues or questions:
1. Check the troubleshooting section above
2. Review the code comments for implementation details
3. Ensure all files (Food.java, Pantry.java, FoodTrackerGUI.java) are in the same directory

---

**Happy tracking! 🍎**
