package fr.central;

import fr.central.bd.Context;
import fr.central.datatype.Article;

public class App {

    public static void main(String[] args) {
        try {
            Context context = Context.getInstance();
            Heptathlon heptathlon = new Heptathlon(context);
            Article res = heptathlon.showStocks(1L).get(0);
            System.out.println(res.ToString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }

    }
}
