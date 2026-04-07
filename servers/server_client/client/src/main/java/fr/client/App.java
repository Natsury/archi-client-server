package fr.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.siege.datatype.Facture;
import fr.siege.interfaces.IHeptathlon_siege;

public class App {

    public static void main(String[] args) {
        try {
            Heptathlon heptathlon = new Heptathlon();

            Registry registry = LocateRegistry.getRegistry("siege_server", 1099);

            IHeptathlon_siege stub = (IHeptathlon_siege) registry.lookup("HeptathlonSiege");
            System.out.println("Heptathlon Siege stub is ready.");

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Saving bills...");
                try {
                    List<Facture> factures = heptathlon.getAllFactures();
                    
                    stub.saveBills(factures);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to save bills: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }, 1, 1, TimeUnit.MINUTES);

            Facture facture = new Facture(0, null, null, 0, new ArrayList<>());
            facture.setNum_Facture(heptathlon.CreateBill(facture));
            heptathlon.BuyProduct(1L, 3, facture.getNum_Facture());
            heptathlon.payBill(facture.getNum_Facture(), "cheque");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }

    }
}
