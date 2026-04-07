package fr.siege.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import fr.siege.datatype.Article;
import fr.siege.datatype.Facture;

public interface IHeptathlon extends Remote{
    List<Article> showStocks(Long reference) throws RemoteException;
    List<Article> getProduct(String type) throws RemoteException;
    boolean BuyProduct(Article article, int factureId) throws RemoteException;
    boolean BuyProduct(Article article, int quantity, int factureId) throws RemoteException;
    boolean BuyProduct(Long reference, int quantity, int factureId) throws RemoteException;
    boolean BuyProduct(Long reference, int factureId) throws RemoteException;
    boolean payBill(int num_Facture, String mode_paiement) throws RemoteException;
    boolean payBill(Facture facture, String mode_paiement) throws RemoteException;
    String showBill(int num_Facture) throws RemoteException;
    String showBill(Facture facture) throws RemoteException;
    String calculateCA() throws RemoteException;
    String calculateCA(LocalDate dateFacturation) throws RemoteException;
    List<Article> addProduct(Article article) throws RemoteException;
    List<Article> addProduct(String type, double prix, int stock) throws RemoteException;
    List<Article> addProducts(List<Article> articles) throws RemoteException;
}
