package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.SalesTrendDTO;
import com.PixelPages.BookStore.dto.TodaySummaryDTO;

import java.util.List;

public interface AnalyticsService {
    TodaySummaryDTO getTodaySummary();
    List<SalesTrendDTO> getDailyTrend(int days);
    List<SalesTrendDTO> getWeeklyTrend(int weeks);
    List<SalesTrendDTO> getMonthlyTrend(int months);
}