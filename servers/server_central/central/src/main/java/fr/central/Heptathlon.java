package fr.central;

import java.rmi.RemoteException;
import java.util.List;

import fr.central.datatype.Article;
import fr.central.datatype.Facture;
import fr.central.interfaces.IHeptathlon;

public abstract class Heptathlon implements IHeptathlon {

    public Heptathlon() {
    }

    @Override
    public void BuyProduct(Long reference, int quantity) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuyProduct'");
    }

    @Override
    public void BuyProduct(Long reference) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuyProduct'");
    }

    @Override
    public Article showStocks(Long reference) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showStocks'");
    }

    @Override
    public Article getProduct(String type) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProduct'");
    }

    @Override
    public void BuyProduct(Article article) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuyProduct'");
    }

    @Override
    public void BuyProduct(Article article, int quantity) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuyProduct'");
    }

    @Override
    public void payBill(int num_Facture) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'payBill'");
    }

    @Override
    public void payBill(Facture facture) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'payBill'");
    }

    @Override
    public void showBill(int num_Facture) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showBill'");
    }

    @Override
    public void showBill(Facture facture) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showBill'");
    }

    @Override
    public void calculateCA() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateCA'");
    }

    @Override
    public Article addProduct(Article article) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public Article addProduct(String type, double prix, int stock) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public List<Article> addProducts(List<Article> articles) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProducts'");
    }
}
