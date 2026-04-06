package fr.central;

import java.util.Iterator;

import fr.central.bd.Context;
import fr.central.datatype.Article;

public class App {

    public static void main(String[] args) {
        try {
            Context context = Context.getInstance();
            Heptathlon heptathlon = new Heptathlon(context);
            Iterator<Article> res = heptathlon.getProduct("Laptop").iterator();
            while (res.hasNext()){
                Article article = res.next();
                System.out.println(article.ToString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }

    }
}
