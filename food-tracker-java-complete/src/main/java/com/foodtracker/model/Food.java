package com.foodtracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private String unit;
    
    @Column(nullable = false)
    private LocalDate expiryDate;
    
    @Column(nullable = false)
    private LocalDate addedDate;
    
    @Column(nullable = false)
    private String category;
    
    // Calculate days until expiry
    @Transient
    public long getDaysUntilExpiry() {
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }
    
    // Check if expired
    @Transient
    public boolean isExpired() {
        return getDaysUntilExpiry() < 0;
    }
    
    // Check if expiring soon (within 3 days)
    @Transient
    public boolean isExpiringSoon() {
        long daysLeft = getDaysUntilExpiry();
        return daysLeft >= 0 && daysLeft <= 3;
    }
    
    // Get status
    @Transient
    public String getStatus() {
        long daysLeft = getDaysUntilExpiry();
        
        if (daysLeft < 0) {
            return "EXPIRED";
        } else if (daysLeft <= 3) {
            return "EXPIRING_SOON";
        } else {
            return "GOOD";
        }
    }
}
