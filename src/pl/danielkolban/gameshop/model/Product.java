package pl.danielkolban.gameshop.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Product implements Serializable, CsvConvertible {
    private String category;
    private String name;
    private double purchasePrice;
    private double sellingPrice;
    private boolean newOrUsed;
    private int quantity;

    public Product(String category, String name, double purchasePrice, double sellingPrice, boolean newOrUsed, int quantity) {
        this.category = category;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.newOrUsed = newOrUsed;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public boolean isNewOrUsed() {
        return newOrUsed;
    }

    public void setNewOrUsed(boolean newOrUsed) {
        this.newOrUsed = newOrUsed;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.purchasePrice, purchasePrice) == 0 && Double.compare(product.sellingPrice, sellingPrice) == 0 && newOrUsed == product.newOrUsed && quantity == product.quantity && Objects.equals(category, product.category) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name, purchasePrice, sellingPrice, newOrUsed, quantity);
    }

    @Override
    public String toString() {
        return category + "; " + name + "; " + newOrUsed;
    }
}
