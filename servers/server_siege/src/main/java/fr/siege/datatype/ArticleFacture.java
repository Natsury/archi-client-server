package fr.siege.datatype;

public class ArticleFacture extends Article{

    private int quantity;

    public ArticleFacture(Long reference, Double price, String type, int stock, int quantity) {
        super(reference, price, type, stock);
        this.quantity = quantity;
    }

    public ArticleFacture(Article article, int quantity) {
        super(article.getReference(), article.getPrice(), article.getType(), article.getStock());
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ArticleFacture{" +
                "reference=" + getReference() +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", stock=" + getStock() +
                ", quantity=" + quantity +
                '}';
    }
}
