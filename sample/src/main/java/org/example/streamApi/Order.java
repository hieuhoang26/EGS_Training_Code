package org.example.streamApi;

import java.util.Objects;

public class Order {
    private int id;
    private String productName;
    private String productType;
    private String vendor;
    private int quantity;
    private double price;

    public Order() {
    }

    public Order(int id, String productName, String productType, String vendor, int quantity, double price) {
        this.id = id;
        this.productName = productName;
        this.productType = productType;
        this.vendor = vendor;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", vendor='" + vendor + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() && getQuantity() == order.getQuantity() && Double.compare(getPrice(), order.getPrice()) == 0 && Objects.equals(getProductName(), order.getProductName()) && Objects.equals(getProductType(), order.getProductType()) && Objects.equals(getVendor(), order.getVendor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getProductType(), getVendor(), getQuantity(), getPrice());
    }
}
