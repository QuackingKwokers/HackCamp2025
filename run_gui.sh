#!/bin/bash

echo "🍎 Compiling Food Tracker GUI..."
javac Food.java Pantry.java FoodTrackerGUI.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo "🚀 Starting Food Tracker GUI..."
    java FoodTrackerGUI
else
    echo "✗ Compilation failed!"
    exit 1
fi
