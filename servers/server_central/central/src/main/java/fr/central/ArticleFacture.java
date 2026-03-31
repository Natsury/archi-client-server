package fr.central;

public class ArticleFacture extends Article{

    private int quantity;

    public ArticleFacture(Long reference, String name, Double price, String type, int quantity) {
        super(reference, name, price, type);
        this.quantity = quantity;
    }

    public ArticleFacture(Article article, int quantity) {
        super(article.getReference(), article.getName(), article.getPrice(), article.getType());
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
