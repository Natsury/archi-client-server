package fr.siege;

import java.rmi.registry.Registry;

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


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Heptathlon server: " + e.getMessage());
        }
    }
}
