# 🍎 Food Tracker GUI - Quick Start Guide

## Get Started in 2 Minutes

### Step 1: Compile the Application
```bash
javac Food.java Pantry.java FoodTrackerGUI.java
```

### Step 2: Run the Application
```bash
java FoodTrackerGUI
```

### That's it! 🎉

The Food Tracker GUI window will open with a clean, user-friendly interface.

---

## First Things to Try

### 1. Load Sample Data
Click **📦 Load Sample Data** to see example foods with various expiration statuses.

### 2. Add a New Food Item
- Click **➕ Add Food**
- Fill in the details (name, quantity, unit, category, expiry date)
- Click **Add**

Example:
- Food Name: Apple
- Quantity: 5
- Unit: pcs
- Category: Produce
- Expiry Date: 2024-12-25

### 3. Explore Filters
Use the **Filter** dropdown to view foods by:
- Status (Expired, Expiring Soon, Good to Use)
- Category (Dairy, Produce, Meat, etc.)

### 4. Remove Items
- Select a food from the table
- Click **❌ Remove**
- Confirm deletion

### 5. View Statistics
Check the top-right corner for live statistics:
- **Total**: All items in pantry
- **Expired**: Items past expiry date
- **Expiring Soon**: Items expiring within 3 days
- **Good**: Fresh items

---

## File Structure

You need these 4 files in the same directory:
1. **Food.java** - Food item model
2. **Pantry.java** - Pantry management
3. **FoodTrackerGUI.java** - GUI application
4. Files you already have (README, etc.)

---

## Color Scheme

| Color | Meaning |
|-------|---------|
| 🟢 Green | Good - More than 3 days remaining |
| 🟡 Yellow | Expiring Soon - 1-3 days remaining |
| 🔴 Red | Expired - Past expiry date |

---

## Keyboard Tips

- **Tab**: Move between fields in the Add Food dialog
- **Enter**: Submit Add Food form
- **Escape**: Close dialogs
- **Click rows**: Select food items for details/removal

---

## Common Tasks

### Add Multiple Items Quickly
1. Click **➕ Add Food**
2. Fill in first item
3. Click **Add**
4. Dialog closes, add next item
5. Repeat!

### Search for an Item
Use the Filter dropdown and look through the table, or:
1. Click **📋 View Details** on any item to see full information
2. The search looks for partial name matches

### Check What's About to Expire
1. Select **Expiring Soon** from the Filter dropdown
2. All items expiring within 3 days appear sorted by urgency

### See Everything at Once
1. Select **All** from the Filter dropdown
2. All items organized by status (Expired → Expiring Soon → Good)

---

## Data

- **Demo Data**: Click **📦 Load Sample Data** (doesn't delete existing items)
- **Persistent**: Data stays in memory during the session
- **Multiple Pantries**: Currently tracks one "My Kitchen" pantry
- **Console Version**: Your original console app still works! Run it with `java FoodTrackerApp`

---

## Features at a Glance

✅ Add/Remove foods  
✅ View expiry status  
✅ Filter by category and status  
✅ Live statistics  
✅ Color-coded status indicators  
✅ Detailed food information  
✅ Sample data for testing  
✅ Clean, intuitive interface  

---

## Troubleshooting

**"javac: command not found"**
→ Install Java JDK and add it to your PATH

**Date format error**
→ Use format: yyyy-MM-dd (e.g., 2024-12-25)

**No GUI appears**
→ Make sure you ran `java FoodTrackerGUI` (not FoodTrackerApp)

**Nothing shows in table**
→ Click **📦 Load Sample Data** or add items with **➕ Add Food**

---

## Next Steps

- Read **README_GUI.md** for detailed feature documentation
- Check the code comments in **FoodTrackerGUI.java** for implementation details
- Try adding different food categories and tracking expiry patterns
- Consider customizing colors or adding more features!

---

**Ready to track your food? Let's go! 🍎**
