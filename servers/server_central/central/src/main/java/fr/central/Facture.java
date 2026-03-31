package fr.central;

import java.io.Serializable;
import java.util.List;

public class Facture implements Serializable{
    private final int num_Facture;
    private final String mode_paiement;
    private final String date_fac;
    private double prix_total;
    private List<ArticleFacture> articles;

    public Facture(int num_Facture, String mode_paiement, String date_fac, double prix_total, List<ArticleFacture> articles) {
        this.num_Facture = num_Facture;
        this.mode_paiement = mode_paiement;
        this.date_fac = date_fac;
        this.prix_total = prix_total;
        this.articles = articles;
    }

    public int getNum_Facture() {
        return num_Facture;
    }

    public String getMode_paiement() {
        return mode_paiement;
    }

    public String getDate_fac() {
        return date_fac;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public List<ArticleFacture> getArticles() {
        return articles;
    }

    public void addArticle(ArticleFacture article) {
        this.articles.add(article);
        this.prix_total += article.getPrice() * article.getQuantity();
    }

}
