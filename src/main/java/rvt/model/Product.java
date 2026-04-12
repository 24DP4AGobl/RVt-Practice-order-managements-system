package rvt.model;

import java.math.BigDecimal;

public class Product {
    
    int id;
    String name;
    BigDecimal price;
    int categoryId;
    int amount;
    int delivererId;

    public Product(int id, String name, BigDecimal price, int categoryId, int amount, int delivererId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.amount = amount;
        this.delivererId = delivererId;
    }

    //setter methods//
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCatId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDelId(int delivererId) {
        this.delivererId = delivererId;
    }

    //getter methods//
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCatId() {
        return categoryId;
    }
    
    public int getAmount() {
        return amount;
    }

    public int getDelId() {
        return delivererId;
    }
}