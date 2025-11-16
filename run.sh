#!/bin/bash
echo "🍎 Compiling Java Food Tracker..."
javac Food.java Pantry.java FoodTrackerApp.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo ""
    echo "Running application..."
    echo ""
    java FoodTrackerApp
else
    echo "❌ Compilation failed!"
    exit 1
fi
