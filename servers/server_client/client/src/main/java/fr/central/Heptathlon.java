package fr.central;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.central.bd.Context;
import fr.central.datatype.Article;
import fr.central.datatype.Facture;
import fr.central.exceptions.NotEnoughStockException;
import fr.central.interfaces.IHeptathlon;

public class Heptathlon implements IHeptathlon {
    private final Context context;

    public Heptathlon(Context context) {
        this.context = context;
    }

    @Override
    public boolean BuyProduct(Long reference, int quantity, int factureId) throws RemoteException {
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

            return BuyProduct(articles.get(0), quantity, factureId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while fetching stocks for reference: " + reference + ", quantity: " + quantity, e);
        }
    }

    @Override
    public boolean BuyProduct(Long reference, int factureId) throws RemoteException {
        try {
            return BuyProduct(reference, 1, factureId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while fetching Article for reference: " + reference, e);
        }
    }

    
    @Override
    public boolean BuyProduct(Article article, int factureId) throws RemoteException {
        try {
            return BuyProduct(article, 1, factureId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while fetching Article for reference: " + article.toString(), e);
        }
    }

    @Override
    public boolean BuyProduct(Article article, int quantity, int factureId) throws RemoteException {
        try {
            if(article.getStock() < quantity) 
                throw new NotEnoughStockException(
                    "Not enough stock for article: " 
                    + article.ToString() 
                    + ", requested quantity: " 
                    + quantity);
            // Get the product in the panier table 
            String query = "SELECT * FROM panier WHERE reference = " 
            + article.getReference()  
            + " AND Num_Facture = " + factureId;

            ResultSet resultSet = context.GetStatement(query);
            if(resultSet.next()) {
                // If the product is already in the panier, update the quantity
                int existingQuantity = resultSet.getInt("Quantite");
                String updateQuery = "UPDATE panier SET "
                + "Quantite = " + (existingQuantity + quantity) 
                + " WHERE reference = " + article.getReference() 
                + " AND Num_Facture = " + factureId;
                context.ExecuteUpdate(updateQuery);
            } else {
                // If the product is not in the panier, insert it
                String insertQuery = "INSERT INTO panier (reference, Num_Facture, Quantite) VALUES (" 
                + article.getReference() + ", " 
                + factureId + ", " 
                + quantity + ")";
                context.ExecuteUpdate(insertQuery);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while buying Product: "
            + article.toString()
            + ", quantity: " + quantity
            + ", factureId: " + factureId, e);
        }
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
