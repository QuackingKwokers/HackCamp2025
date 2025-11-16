@echo off
echo 🍎 Compiling Java Food Tracker...
javac Food.java Pantry.java FoodTrackerApp.java

if %errorlevel% equ 0 (
    echo ✓ Compilation successful!
    echo.
    echo Running application...
    echo.
    java FoodTrackerApp
) else (
    echo ❌ Compilation failed!
    exit /b 1
)
