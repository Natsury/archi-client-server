package fr.central.datatype;

public class ArticleFacture extends Article{

    private int quantity;

    public ArticleFacture(Long reference, String name, Double price, String type, int quantity, int stock) {
        super(reference, name, price, type, stock);
        this.quantity = quantity;
    }

    public ArticleFacture(Article article, int quantity) {
        super(article.getReference(), article.getName(), article.getPrice(), article.getType(), article.getStock());
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
