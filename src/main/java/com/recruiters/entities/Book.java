package com.recruiters.entities;

public class Book {
    private String name;
    private Double price;
    private Boolean available;

    public Book(final String name, final Double price, final Boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(final Boolean available) {
        this.available = available;
    }
}
