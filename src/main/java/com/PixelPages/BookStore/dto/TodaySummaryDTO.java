package com.PixelPages.BookStore.dto;

public class TodaySummaryDTO {
    private String date;
    private long booksSoldToday;
    private double revenueToday;
    private long returnsToday;
    private double refundAmountToday;

    public TodaySummaryDTO() {}

    public TodaySummaryDTO(String date, long booksSoldToday, double revenueToday,
                           long returnsToday, double refundAmountToday) {
        this.date = date;
        this.booksSoldToday = booksSoldToday;
        this.revenueToday = revenueToday;
        this.returnsToday = returnsToday;
        this.refundAmountToday = refundAmountToday;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public long getBooksSoldToday() { return booksSoldToday; }
    public void setBooksSoldToday(long booksSoldToday) { this.booksSoldToday = booksSoldToday; }
    public double getRevenueToday() { return revenueToday; }
    public void setRevenueToday(double revenueToday) { this.revenueToday = revenueToday; }
    public long getReturnsToday() { return returnsToday; }
    public void setReturnsToday(long returnsToday) { this.returnsToday = returnsToday; }
    public double getRefundAmountToday() { return refundAmountToday; }
    public void setRefundAmountToday(double refundAmountToday) { this.refundAmountToday = refundAmountToday; }
}