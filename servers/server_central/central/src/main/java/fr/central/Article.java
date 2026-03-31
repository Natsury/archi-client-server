package fr.central;

import java.io.Serializable;

public class Article implements Serializable {
    
    private final Long reference;
    private final String name;
    private final Double price;
    private final String type;

    public Article(Long reference, String name, Double price, String type) {
        this.reference = reference;
        this.name = name;
        this.price = price;
        this.type = type;
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

    public String ToString() {
        return "Article :" +
                "reference = " + reference +
                ", name = '" + name + '\'' +
                ", price = " + price +
                ", type = '" + type + '\'';
    }
}
