package com.PixelPages.BookStore.dto;

public class SalesTrendDTO {
    private String period;       // e.g. "2026-09-17", "2026-W38", "2026-09"
    private long booksSold;
    private double revenue;

    public SalesTrendDTO() {}

    public SalesTrendDTO(String period, long booksSold, double revenue) {
        this.period = period;
        this.booksSold = booksSold;
        this.revenue = revenue;
    }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public long getBooksSold() { return booksSold; }
    public void setBooksSold(long booksSold) { this.booksSold = booksSold; }
    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
}