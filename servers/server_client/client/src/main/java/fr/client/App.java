package fr.client;

import java.util.List;

import fr.client.bd.Context;
import fr.client.datatype.Article;

public class App {

    public static void main(String[] args) {
        try {
            Context context = Context.getInstance();
            Heptathlon heptathlon = new Heptathlon(context);

            // PreparedStatement stmt = Context.getInstance().ExecuteUpdate(
            //     "INSERT INTO FACTURE (Date_fac, Prix_total, Mode_paiement) VALUES (null, 0, 'null')"
            // );

            // ResultSet resultSet = stmt.getGeneratedKeys();

            // resultSet.next();
            // int factureId = resultSet.getInt(1);

            Article article = new Article(null, 10.0, "TEST", 615);
            Article article2 = new Article(null, 20.0, "test", 816457);

            List<Article> articles = List.of(article, article2);
            System.out.println(heptathlon.addProducts(articles));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }

    }
}
