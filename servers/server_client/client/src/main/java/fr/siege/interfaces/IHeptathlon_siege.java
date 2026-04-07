package fr.siege.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fr.siege.datatype.Facture;

public interface  IHeptathlon_siege extends Remote{
     void updatePrice(Long reference, double newPrice) throws RemoteException;
    void updatePrices() throws RemoteException;
    void saveBills(List<Facture> facture) throws RemoteException;
    void saveBill(int num_Facture) throws RemoteException;
}
