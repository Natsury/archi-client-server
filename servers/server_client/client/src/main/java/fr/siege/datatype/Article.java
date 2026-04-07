package fr.siege.datatype;

import java.io.Serializable;

public class Article implements Serializable {
    
    private final Long reference;
    private final Double price;
    private final String type;
    private final int stock;

    public Article(Long reference, Double price, String type, int stock) {
        this.reference = reference;
        this.price = price;
        this.type = type;
        this.stock = stock;
    }

    public Long getReference() {
        return reference;
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

    @Override
    public String toString() {
        return "Article :" +
                "reference = " + reference +
                ", price = " + price +
                ", type = '" + type + '\'' +
                ", stock = " + stock;
    }
}
