package com.foodtracker.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private String unit;
    private LocalDate expiryDate;
    private LocalDate addedDate;
    private String category;
    private String status;
    private Long daysUntilExpiry;
}
