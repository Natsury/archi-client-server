package fr.siege;

import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.siege.interfaces.IHeptathlon_siege;

public class App {

    public static void main(String[] args) {
        try {
            Heptathlon_siege heptathlonSiege = new Heptathlon_siege();

            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(1099);

            IHeptathlon_siege stub = (IHeptathlon_siege) java.rmi.server.UnicastRemoteObject
            .exportObject(heptathlonSiege, 1099);

            registry.bind("HeptathlonSiege", stub);
            System.out.println("Heptathlon Siege server is ready.");

            System.out.println("Starting cron job to update prices...");

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Updating prices...");
                try {
                    heptathlonSiege.updatePrices(1+0.1);
                    System.out.println("Prices updated successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to update prices: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }, 1, 1, TimeUnit.MINUTES);


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }
    }
}
