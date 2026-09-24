package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.SalesTrendDTO;
import com.PixelPages.BookStore.dto.TodaySummaryDTO;
import com.PixelPages.BookStore.repository.OrderItemRepository;
import com.PixelPages.BookStore.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ReturnRepository returnRepository;

    @Override
    public TodaySummaryDTO getTodaySummary() {
        Long booksSold = orderItemRepository.countBooksSoldToday();
        Double revenue = orderItemRepository.sumRevenueToday();
        Long returnsToday = returnRepository.countReturnsToday();
        Double refundAmount = returnRepository.sumRefundAmountToday();

        return new TodaySummaryDTO(
                LocalDate.now().toString(),
                booksSold != null ? booksSold : 0,
                revenue != null ? revenue : 0.0,
                returnsToday != null ? returnsToday : 0,
                refundAmount != null ? refundAmount : 0.0
        );
    }

    @Override
    public List<SalesTrendDTO> getDailyTrend(int days) {
        LocalDateTime fromDate = LocalDateTime.now().minusDays(days);
        return orderItemRepository.getDailyTrend(fromDate).stream()
                .map(row -> new SalesTrendDTO(row[0].toString(),
                        ((Number) row[1]).longValue(), ((Number) row[2]).doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesTrendDTO> getWeeklyTrend(int weeks) {
        LocalDateTime fromDate = LocalDateTime.now().minusWeeks(weeks);
        return orderItemRepository.getWeeklyTrend(fromDate).stream()
                .map(row -> new SalesTrendDTO(row[0].toString(),
                        ((Number) row[1]).longValue(), ((Number) row[2]).doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesTrendDTO> getMonthlyTrend(int months) {
        LocalDateTime fromDate = LocalDateTime.now().minusMonths(months);
        return orderItemRepository.getMonthlyTrend(fromDate).stream()
                .map(row -> new SalesTrendDTO(row[0].toString(),
                        ((Number) row[1]).longValue(), ((Number) row[2]).doubleValue()))
                .collect(Collectors.toList());
    }
}