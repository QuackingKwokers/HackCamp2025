package com.foodtracker.service;

import com.foodtracker.model.Food;
import com.foodtracker.dto.FoodDTO;
import com.foodtracker.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    
    // Convert Food entity to DTO
    public FoodDTO convertToDTO(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .name(food.getName())
                .quantity(food.getQuantity())
                .unit(food.getUnit())
                .expiryDate(food.getExpiryDate())
                .addedDate(food.getAddedDate())
                .category(food.getCategory())
                .status(food.getStatus())
                .daysUntilExpiry(food.getDaysUntilExpiry())
                .build();
    }
    
    // Convert DTO to Food entity
    public Food convertToEntity(FoodDTO dto) {
        return Food.builder()
                .id(dto.getId())
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .unit(dto.getUnit())
                .expiryDate(dto.getExpiryDate())
                .addedDate(dto.getAddedDate() != null ? dto.getAddedDate() : LocalDate.now())
                .category(dto.getCategory())
                .build();
    }
    
    // Add a new food
    public FoodDTO addFood(FoodDTO foodDTO) {
        Food food = convertToEntity(foodDTO);
        if (food.getAddedDate() == null) {
            food.setAddedDate(LocalDate.now());
        }
        Food saved = foodRepository.save(food);
        return convertToDTO(saved);
    }
    
    // Get all foods
    public List<FoodDTO> getAllFoods() {
        return foodRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get food by ID
    public FoodDTO getFoodById(Long id) {
        return foodRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    // Update a food
    public FoodDTO updateFood(Long id, FoodDTO foodDTO) {
        return foodRepository.findById(id)
                .map(food -> {
                    food.setName(foodDTO.getName());
                    food.setQuantity(foodDTO.getQuantity());
                    food.setUnit(foodDTO.getUnit());
                    food.setExpiryDate(foodDTO.getExpiryDate());
                    food.setCategory(foodDTO.getCategory());
                    Food updated = foodRepository.save(food);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }
    
    // Delete a food
    public boolean deleteFood(Long id) {
        if (foodRepository.existsById(id)) {
            foodRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Get expired foods
    public List<FoodDTO> getExpiredFoods() {
        return foodRepository.findAll()
                .stream()
                .filter(Food::isExpired)
                .sorted((a, b) -> Long.compare(b.getDaysUntilExpiry(), a.getDaysUntilExpiry()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get expiring soon foods (within 3 days)
    public List<FoodDTO> getExpiringSoon() {
        return foodRepository.findAll()
                .stream()
                .filter(food -> food.isExpiringSoon() && !food.isExpired())
                .sorted((a, b) -> Long.compare(a.getDaysUntilExpiry(), b.getDaysUntilExpiry()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get good foods (not expiring soon)
    public List<FoodDTO> getGoodFoods() {
        return foodRepository.findAll()
                .stream()
                .filter(food -> !food.isExpired() && !food.isExpiringSoon())
                .sorted((a, b) -> Long.compare(a.getDaysUntilExpiry(), b.getDaysUntilExpiry()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Search foods by name
    public List<FoodDTO> searchFoods(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get foods by category
    public List<FoodDTO> getFoodsByCategory(String category) {
        return foodRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get statistics
    public StatisticsDTO getStatistics() {
        List<Food> allFoods = foodRepository.findAll();
        long expired = allFoods.stream().filter(Food::isExpired).count();
        long expiringSoon = allFoods.stream().filter(food -> food.isExpiringSoon() && !food.isExpired()).count();
        long good = allFoods.stream().filter(food -> !food.isExpired() && !food.isExpiringSoon()).count();
        
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        long expiringThisWeek = allFoods.stream()
                .filter(food -> food.getExpiryDate().isBefore(nextWeek) && !food.isExpired())
                .count();
        
        return StatisticsDTO.builder()
                .totalItems(allFoods.size())
                .expiredCount((int) expired)
                .expiringSoonCount((int) expiringSoon)
                .goodCount((int) good)
                .expiringThisWeekCount((int) expiringThisWeek)
                .build();
    }
    
    // Add sample data
    public void addSampleData() {
        LocalDate today = LocalDate.now();
        
        addFood(FoodDTO.builder()
                .name("Milk")
                .quantity(1)
                .unit("L")
                .expiryDate(today.plusDays(2))
                .category("dairy")
                .build());
        
        addFood(FoodDTO.builder()
                .name("Bread")
                .quantity(1)
                .unit("pcs")
                .expiryDate(today.plusDays(1))
                .category("pantry")
                .build());
        
        addFood(FoodDTO.builder()
                .name("Tomato")
                .quantity(3)
                .unit("pcs")
                .expiryDate(today.minusDays(1))
                .category("produce")
                .build());
        
        addFood(FoodDTO.builder()
                .name("Chicken")
                .quantity(500)
                .unit("g")
                .expiryDate(today.plusDays(5))
                .category("meat")
                .build());
        
        addFood(FoodDTO.builder()
                .name("Yogurt")
                .quantity(2)
                .unit("pcs")
                .expiryDate(today.plusDays(3))
                .category("dairy")
                .build());
        
        addFood(FoodDTO.builder()
                .name("Lettuce")
                .quantity(1)
                .unit("pcs")
                .expiryDate(today.plusDays(2))
                .category("produce")
                .build());
        
        addFood(FoodDTO.builder()
                .name("Ice Cream")
                .quantity(1)
                .unit("L")
                .expiryDate(today.plusDays(30))
                .category("frozen")
                .build());
    }
}
