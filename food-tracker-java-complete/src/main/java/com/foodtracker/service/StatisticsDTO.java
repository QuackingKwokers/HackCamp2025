package com.foodtracker.service;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsDTO {
    private int totalItems;
    private int expiredCount;
    private int expiringSoonCount;
    private int goodCount;
    private int expiringThisWeekCount;
}
