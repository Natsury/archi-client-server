package fr.central;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.central.bd.Context;
import fr.central.datatype.Article;
import fr.central.datatype.Facture;
import fr.central.interfaces.IHeptathlon;

public class Heptathlon implements IHeptathlon {
    private final Context context;

    public Heptathlon(Context context) {
        this.context = context;
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
    public List<Article> showStocks(Long reference) throws RemoteException {
        String query = "SELECT * FROM article WHERE Reference = " + reference;
        List<Article> articles = new ArrayList<>();
        ResultSet resultSet = context.GetStatement(query);
        try {
            while (resultSet.next()) {
                Article article = new Article(
                        resultSet.getLong("reference"),
                        resultSet.getDouble("prix"),
                        resultSet.getString("type"),
                        resultSet.getInt("stock")
                );  
                articles.add(article);              
            }

            return articles;
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while fetching stocks for reference: " + reference, e);
        }
    }

    @Override
    public List<Article> getProduct(String type) throws RemoteException {
        String query = "SELECT * FROM article WHERE Type = '" + type + "'";
        List<Article> articles = new ArrayList<>();
        ResultSet resultSet = context.GetStatement(query);
        try {
            while (resultSet.next()) {
                Article article = new Article(
                        resultSet.getLong("reference"),
                        resultSet.getDouble("prix"),
                        resultSet.getString("type"),
                        resultSet.getInt("stock")
                ); 
                if(article.getStock() > 0) articles.add(article);              
            }

            return articles;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while fetching stocks for type: " + type, e);
        }
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
