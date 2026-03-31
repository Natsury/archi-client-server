package fr.central;

import java.io.Serializable;

public class Article implements Serializable {
    
    private final Long reference;
    private final String name;
    private final Double price;
    private final String type;
    private final int stock;

    public Article(Long reference, String name, Double price, String type, int stock) {
        this.reference = reference;
        this.name = name;
        this.price = price;
        this.type = type;
        this.stock = stock;
    }

    public Long getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getType(){
        return type;
    }

    public int getStock() {
        return stock;
    }

    public String ToString() {
        return "Article :" +
                "reference = " + reference +
                ", name = '" + name + '\'' +
                ", price = " + price +
                ", type = '" + type + '\'' +
                ", stock = " + stock;
    }
}
