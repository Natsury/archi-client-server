package fr.central.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fr.central.datatype.Article;
import fr.central.datatype.Facture;

public interface IHeptathlon extends Remote{
    List<Article> showStocks(Long reference) throws RemoteException;
    List<Article> getProduct(String type) throws RemoteException;
    boolean BuyProduct(Article article, int factureId) throws RemoteException;
    boolean BuyProduct(Article article, int quantity, int factureId) throws RemoteException;
    boolean BuyProduct(Long reference, int quantity, int factureId) throws RemoteException;
    boolean BuyProduct(Long reference, int factureId) throws RemoteException;
    void payBill(int num_Facture) throws RemoteException;
    void payBill(Facture facture) throws RemoteException;
    void showBill(int num_Facture) throws RemoteException;
    void showBill(Facture facture) throws RemoteException;
    void calculateCA() throws RemoteException;
    Article addProduct(Article article) throws RemoteException;
    Article addProduct(String type, double prix, int stock) throws RemoteException;
    List<Article> addProducts(List<Article> articles) throws RemoteException;
}
