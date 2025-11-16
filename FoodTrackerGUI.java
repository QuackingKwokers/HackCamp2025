import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FoodTrackerGUI extends JFrame {
    private Pantry pantry;
    private JPanel mainPanel;
    private JTable foodTable;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateFormatter;
    
    // Main panel components
    private JLabel statsLabel;
    private JComboBox<String> filterCombo;
    
    public FoodTrackerGUI() {
        this.pantry = new Pantry("My Kitchen");
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        setTitle("🍎 Food Expiry Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        
        initializeUI();
    }
    
    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with title and controls
        JPanel topPanel = createTopPanel();
        
        // Center panel with table
        JPanel centerPanel = createCenterPanel();
        
        // Bottom panel with action buttons
        JPanel bottomPanel = createBottomPanel();
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        
        // Title and stats
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("🍎 Food Expiry Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        
        // Stats label
        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        updateStatsLabel();
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statsPanel.add(statsLabel);
        
        topPanel.add(titlePanel, BorderLayout.WEST);
        topPanel.add(statsPanel, BorderLayout.EAST);
        
        // Filter and search panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter:"));
        
        filterCombo = new JComboBox<>(new String[]{"All", "Expired", "Expiring Soon", "Good to Use", "Dairy", "Produce", "Meat", "Pantry", "Frozen", "Other"});
        filterCombo.addActionListener(e -> updateTableData());
        filterPanel.add(filterCombo);
        
        topPanel.add(filterPanel, BorderLayout.SOUTH);
        
        return topPanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Create table
        String[] columnNames = {"Name", "Quantity", "Unit", "Category", "Expiry Date", "Days Left", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        foodTable = new JTable(tableModel);
        foodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        foodTable.setRowHeight(25);
        
        // Set column widths
        foodTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        foodTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        foodTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        foodTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        foodTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        foodTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        foodTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        
        // Add custom cell renderer for status colors
        foodTable.getColumnModel().getColumn(6).setCellRenderer(new StatusCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(foodTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        updateTableData();
        
        return centerPanel;
    }
    
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("➕ Add Food");
        addButton.addActionListener(e -> showAddFoodDialog());
        
        JButton viewDetailsButton = new JButton("📋 View Details");
        viewDetailsButton.addActionListener(e -> viewSelectedFoodDetails());
        
        JButton removeButton = new JButton("❌ Remove");
        removeButton.addActionListener(e -> removeSelectedFood());
        
        JButton sampleDataButton = new JButton("📦 Load Sample Data");
        sampleDataButton.addActionListener(e -> loadSampleData());
        
        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.addActionListener(e -> updateTableData());
        
        bottomPanel.add(addButton);
        bottomPanel.add(viewDetailsButton);
        bottomPanel.add(removeButton);
        bottomPanel.add(sampleDataButton);
        bottomPanel.add(refreshButton);
        
        return bottomPanel;
    }
    
    private void showAddFoodDialog() {
        JDialog dialog = new JDialog(this, "Add Food", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        // Main panel using BoxLayout (vertical)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Helper function to create row
        JTextField nameField = createFormRow(mainPanel, "Food Name:", new JTextField(25));
        JTextField quantityField = createFormRow(mainPanel, "Quantity:", new JTextField(25));
        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"pcs", "kg", "L", "g", "ml"});
        createFormRow(mainPanel, "Unit:", unitCombo);
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"dairy", "produce", "meat", "pantry", "frozen", "other"});
        createFormRow(mainPanel, "Category:", categoryCombo);
        JTextField dateField = createFormRow(mainPanel, "Expiry Date (yyyy-MM-dd):", new JTextField(25));
        
        // Spacing
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = new JButton("✓ Add Food");
        JButton cancelButton = new JButton("✗ Cancel");
        
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Food name cannot be empty!");
                    nameField.requestFocus();
                    return;
                }
                
                int quantity = Integer.parseInt(quantityField.getText().trim());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Quantity must be greater than 0!");
                    quantityField.requestFocus();
                    return;
                }
                
                String unit = (String) unitCombo.getSelectedItem();
                String category = (String) categoryCombo.getSelectedItem();
                LocalDate expiryDate = LocalDate.parse(dateField.getText().trim(), dateFormatter);
                
                pantry.addFood(name, quantity, unit, expiryDate, category);
                updateTableData();
                updateStatsLabel();
                dialog.dispose();
                
                JOptionPane.showMessageDialog(this, "✓ Food added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid quantity! Must be a whole number.");
                quantityField.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid date! Use format: yyyy-MM-dd");
                dateField.requestFocus();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    // Helper method to create form rows
    private <T extends JComponent> T createFormRow(JPanel parent, String label, T component) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(400, 40));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(180, 30));
        labelComponent.setFont(new Font("Arial", Font.PLAIN, 13));
        
        if (component instanceof JTextField) {
            ((JTextField) component).setFont(new Font("Arial", Font.PLAIN, 13));
        } else if (component instanceof JComboBox) {
            ((JComboBox<?>) component).setFont(new Font("Arial", Font.PLAIN, 13));
        }
        component.setMaximumSize(new Dimension(250, 35));
        component.setPreferredSize(new Dimension(250, 35));
        
        row.add(labelComponent);
        row.add(Box.createHorizontalStrut(10));
        row.add(component);
        row.add(Box.createHorizontalGlue());
        
        parent.add(row);
        parent.add(Box.createVerticalStrut(8));
        
        return component;
    }
    
    private void viewSelectedFoodDetails() {
        int selectedRow = foodTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a food item first!");
            return;
        }
        
        String foodName = (String) tableModel.getValueAt(selectedRow, 0);
        List<Food> foods = pantry.searchFood(foodName);
        
        if (!foods.isEmpty()) {
            Food food = foods.get(0);
            JOptionPane.showMessageDialog(this, food.getDetails(), "Food Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void removeSelectedFood() {
        int selectedRow = foodTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a food item to remove!");
            return;
        }
        
        String foodName = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to remove " + foodName + "?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            pantry.removeFood(foodName);
            updateTableData();
            updateStatsLabel();
        }
    }
    
    private void loadSampleData() {
        LocalDate today = LocalDate.now();
        
        pantry.addFood("Milk", 1, "L", today.plusDays(2), "dairy");
        pantry.addFood("Bread", 1, "pcs", today.plusDays(1), "pantry");
        pantry.addFood("Tomato", 3, "pcs", today.minusDays(1), "produce");
        pantry.addFood("Chicken", 500, "g", today.plusDays(5), "meat");
        pantry.addFood("Yogurt", 2, "pcs", today.plusDays(3), "dairy");
        pantry.addFood("Lettuce", 1, "pcs", today.plusDays(2), "produce");
        pantry.addFood("Ice Cream", 1, "L", today.plusDays(30), "frozen");
        
        updateTableData();
        updateStatsLabel();
        JOptionPane.showMessageDialog(this, "✓ Sample data loaded!");
    }
    
    private void updateTableData() {
        tableModel.setRowCount(0);
        
        List<Food> foods = null;
        String filter = (String) filterCombo.getSelectedItem();
        
        switch (filter) {
            case "Expired":
                foods = pantry.getExpiredFoods();
                break;
            case "Expiring Soon":
                foods = pantry.getExpiringSoon();
                break;
            case "Good to Use":
                foods = pantry.getGoodFoods();
                break;
            case "Dairy":
            case "Produce":
            case "Meat":
            case "Pantry":
            case "Frozen":
            case "Other":
                foods = pantry.getFoodsByCategory(filter.toLowerCase());
                break;
            case "All":
            default:
                foods = pantry.getAllFoods();
                break;
        }
        
        for (Food food : foods) {
            Object[] row = {
                food.getName(),
                food.getQuantity(),
                food.getUnit(),
                food.getCategory(),
                food.getExpiryDate(),
                food.getDaysUntilExpiry(),
                food.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateStatsLabel() {
        int total = pantry.getAllFoods().size();
        int expired = pantry.getTotalExpiredCount();
        int expiringSoon = pantry.getExpiringSoon().size();
        int good = pantry.getGoodFoods().size();
        
        statsLabel.setText(String.format(
            "Total: %d | Expired: %d | Expiring Soon: %d | Good: %d",
            total, expired, expiringSoon, good
        ));
    }
    
    // Custom cell renderer for status column
    private static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                String status = (String) value;
                if ("EXPIRED".equals(status)) {
                    c.setBackground(new Color(255, 200, 200));
                    c.setForeground(Color.RED);
                } else if ("EXPIRING SOON".equals(status)) {
                    c.setBackground(new Color(255, 255, 200));
                    c.setForeground(new Color(200, 150, 0));
                } else if ("GOOD".equals(status)) {
                    c.setBackground(new Color(200, 255, 200));
                    c.setForeground(Color.GREEN);
                }
            }
            
            ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FoodTrackerGUI frame = new FoodTrackerGUI();
            frame.setVisible(true);
        });
    }
}
