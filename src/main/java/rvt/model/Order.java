package rvt.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    
    int id;
    String date;
    BigDecimal total;
    int statusId;
    int employeeId;
    int productId;
    int amount;

    public Order(int id, String date, BigDecimal total, int amount, int statusId, int employeeId, int productId) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.amount = amount;
        this.statusId = statusId;
        this.employeeId = employeeId;
        this.productId = productId;
    }

    //setter methods//
    public void setId(int id) {
        this.id = id;
    }

    public void setDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.date = now.format(formatter);
    }

    public void setPhone(BigDecimal total) {
        this.total = total;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStatId(int statusId) {
        this.statusId = statusId;
    }

    public void setEmplId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setProdlId(int productId) {
        this.productId = productId;
    }

    //getter methods//
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public int getAmount() {
        return amount;
    }

    public int getStatId() {
        return statusId;
    }

    public int getEmplId() {
        return employeeId;
    }

    public int getProdlId() {
        return productId;
    }

    @Override
    public String toString() {
        return "Pasūtījums #" + id + " | " + date + " | " + total + "€";
    }
}
