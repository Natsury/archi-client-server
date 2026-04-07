package fr.siege.datatype;

import java.io.Serializable;
import java.util.List;

public class Facture implements Serializable{
    private int num_Facture;
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

    public void setNum_Facture(int num_Facture) {
        this.num_Facture = num_Facture;
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
        System.out.println("Added article to facture: " + article.toString());
        this.articles.add(article);
        this.prix_total += article.getPrice() * article.getQuantity();

        System.out.println("Updated facture total price: " + this.prix_total);
    }

    @Override
    public String toString() {
        String string = "Facture{" +
            "num_Facture=" + num_Facture +
            ", mode_paiement='" + mode_paiement + '\'' +
            ", date_fac='" + date_fac + '\'' +
            ", prix_total=" + prix_total + ", ";

            for (ArticleFacture article : articles) {
                string += "\narticle=" + article.toString() + ", ";
            }
        
        return string + '}';
    }

}
