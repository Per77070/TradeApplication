package com.example.tradeapplication;

public class Note {
    private String volume;
    private String stockCompany;
    private String stockValue;

    public Note(String volume, String stockCompany, String stockValue) {
        this.volume = volume;
        this.stockCompany = stockCompany;
        this.stockValue = stockValue;
    }

    public Note() {

    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setStockCompany(String stockCompany) {
        this.stockCompany = stockCompany;
    }

    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }

    public String getVolume() {
        return volume;
    }

    public String getStockCompany() {
        return stockCompany;
    }

    public String getStockValue() {
        return stockValue;
    }
}