package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.SalesTrendDTO;
import com.PixelPages.BookStore.dto.TodaySummaryDTO;
import com.PixelPages.BookStore.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    // GET /api/analytics/today
    @GetMapping("/today")
    public ResponseEntity<TodaySummaryDTO> getTodaySummary() {
        return ResponseEntity.ok(analyticsService.getTodaySummary());
    }

    // GET /api/analytics/trend/daily?days=7
    @GetMapping("/trend/daily")
    public ResponseEntity<List<SalesTrendDTO>> getDailyTrend(
            @RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(analyticsService.getDailyTrend(days));
    }

    // GET /api/analytics/trend/weekly?weeks=4
    @GetMapping("/trend/weekly")
    public ResponseEntity<List<SalesTrendDTO>> getWeeklyTrend(
            @RequestParam(defaultValue = "4") int weeks) {
        return ResponseEntity.ok(analyticsService.getWeeklyTrend(weeks));
    }

    // GET /api/analytics/trend/monthly?months=6
    @GetMapping("/trend/monthly")
    public ResponseEntity<List<SalesTrendDTO>> getMonthlyTrend(
            @RequestParam(defaultValue = "6") int months) {
        return ResponseEntity.ok(analyticsService.getMonthlyTrend(months));
    }
}