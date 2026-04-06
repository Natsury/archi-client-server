package fr.central;

import fr.central.bd.Context;

public class App {

    public static void main(String[] args) {
        try {
            Context context = Context.getInstance();
            Heptathlon heptathlon = new Heptathlon(context);

            Context.getInstance().ExecuteUpdate(
                "INSERT INTO FACTURE (Date_fac, Prix_total, Mode_paiement) VALUES ('2026-01-01', 0, 'null')"
            );

            System.out.println(heptathlon.BuyProduct(1L, 1));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }

    }
}
