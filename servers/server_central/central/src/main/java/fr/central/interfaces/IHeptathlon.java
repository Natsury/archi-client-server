package fr.central.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fr.central.Article;
import fr.central.Facture;

public interface IHeptathlon extends Remote{
    Article showStocks(Long reference) throws RemoteException;
    Article getProduct(String type) throws RemoteException;
    void BuyProduct(Article article) throws RemoteException;
    void BuyProduct(Article article, int quantity) throws RemoteException;
    void BuyProduct(Long reference, int quantity) throws RemoteException;
    void BuyProduct(Long reference) throws RemoteException;
    void payBill(int num_Facture) throws RemoteException;
    void payBill(Facture facture) throws RemoteException;
    void showBill(int num_Facture) throws RemoteException;
    void showBill(Facture facture) throws RemoteException;
    void calculateCA() throws RemoteException;
    Article addProduct(Article article) throws RemoteException;
    Article addProduct(String type, double prix, int stock) throws RemoteException;
    List<Article> addProducts(List<Article> articles) throws RemoteException;
}
