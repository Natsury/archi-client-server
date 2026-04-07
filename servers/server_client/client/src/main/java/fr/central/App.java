package fr.central;

import java.time.LocalDate;

import fr.central.bd.Context;

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

            System.out.println(heptathlon.calculateCA(LocalDate.of(2026, 4, 6)));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }

    }
}
