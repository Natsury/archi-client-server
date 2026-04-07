package fr.client;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.client.bd.Context;
import fr.client.datatype.Article;
import fr.client.datatype.ArticleFacture;
import fr.client.datatype.Facture;
import fr.client.exceptions.NotEnoughStockException;
import fr.client.interfaces.IHeptathlon;

public class Heptathlon implements IHeptathlon {
    private final Context context;

    public Heptathlon(Context context) {
        this.context = context;
    }

    @Override
    public boolean BuyProduct(Long reference, int quantity, int factureId) throws RemoteException {
        String query = "SELECT * FROM article WHERE Reference = " + reference;
        ResultSet resultSet = context.GetStatement(query);
        try {
            if (!resultSet.next()) {
                throw new RemoteException("Article with reference: " + reference + " not found");
            }

            Article article = new Article(
                        resultSet.getLong("reference"),
                        resultSet.getDouble("prix"),
                        resultSet.getString("type"),
                        resultSet.getInt("stock")
            );

            System.out.println("Fetched article: " + article.toString());

            return BuyProduct(article, quantity, factureId);
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
                    + article.toString() 
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
    public boolean payBill(int num_Facture, String mode_paiement) throws RemoteException {
        try {
            String query = "SELECT * FROM facture WHERE Num_Facture = " + num_Facture;
            ResultSet resultSet = context.GetStatement(query);
            if(!resultSet.next()) {
                throw new RemoteException("Facture with Num_Facture: " + num_Facture + " not found");
            }

            Facture facture = new Facture(
                resultSet.getInt("Num_Facture"),
                resultSet.getString("mode_paiement"),
                resultSet.getString("date_fac"),
                resultSet.getDouble("prix_total"),
                new ArrayList<>()
            );

            ResultSet articlesResultSet = context.GetStatement("SELECT * FROM panier WHERE Num_Facture = " + num_Facture);
            while (articlesResultSet.next()) {

                ResultSet article = context.GetStatement(
                    "SELECT * FROM article WHERE reference = " + articlesResultSet.getLong("reference"));

                if(!article.next()) {
                    throw new RemoteException("Article with reference: " + articlesResultSet.getLong("reference") + " not found");
                }
                
                ArticleFacture articleFacture = new ArticleFacture(
                    articlesResultSet.getLong("reference"),
                    article.getDouble("prix"),
                    article.getString("type"),
                    article.getInt("stock"),
                    articlesResultSet.getInt("Quantite")
                );
                facture.addArticle(articleFacture);
            }

            return this.payBill(facture, mode_paiement);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while paying bill for facture: " + num_Facture, e);
        }
    }

    @Override
    public boolean payBill(Facture facture, String mode_paiement) throws RemoteException {
        try {
            String updateQuery = 
                "UPDATE facture SET mode_paiement = '" + mode_paiement + "',"
                + "Date_fac = '" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "',"
                + "Prix_total = " + facture.getPrix_total() + " "
                + "WHERE Num_Facture = " + facture.getNum_Facture();
            context.ExecuteUpdate(updateQuery);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while paying bill for facture: " + facture.toString(), e);
        }
    }

    @Override
    public String showBill(int num_Facture) throws RemoteException {
        try {
            String query = "SELECT * FROM facture WHERE Num_Facture = " + num_Facture;
            ResultSet resultSet = context.GetStatement(query);
            if(!resultSet.next()) {
                throw new RemoteException("Facture with Num_Facture: " + num_Facture + " not found");
            }

            Facture facture = new Facture(
                resultSet.getInt("Num_Facture"),
                resultSet.getString("mode_paiement"),
                resultSet.getString("date_fac"),
                resultSet.getDouble("prix_total"),
                new ArrayList<>()
            );

            return showBill(facture);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while showing bill for facture: " + num_Facture, e);
        }

    }

    @Override
    public String showBill(Facture facture) throws RemoteException {
        try {
            ResultSet articlesResultSet = context.GetStatement("SELECT * FROM panier WHERE Num_Facture = " + facture.getNum_Facture());
            while (articlesResultSet.next()) {

                ResultSet article = context.GetStatement(
                    "SELECT * FROM article WHERE reference = " + articlesResultSet.getLong("reference"));

                if(!article.next()) {
                    throw new RemoteException("Article with reference: " + articlesResultSet.getLong("reference") + " not found");
                }
                
                ArticleFacture articleFacture = new ArticleFacture(
                    articlesResultSet.getLong("reference"),
                    article.getDouble("prix"),
                    article.getString("type"),
                    article.getInt("stock"),
                    articlesResultSet.getInt("Quantite")
                );
                facture.addArticle(articleFacture);
            }

            return facture.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while showing bill for facture: " + facture.toString(), e);
        }
    }

    @Override
    public String calculateCA() throws RemoteException {
        try {
            return calculateCA(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while calculating CA", e);
        }
    }

    @Override
    public String calculateCA(LocalDate dateFacturation) throws RemoteException {
        String query = "SELECT SUM(Prix_total) as CA FROM facture " 
        + "WHERE Date_fac = '" + dateFacturation.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "'";
        try {
            ResultSet resultSet = context.GetStatement(query);
            if (!resultSet.next()) {
                throw new RemoteException("Error while calculating CA for date: " + dateFacturation.toString());
            
            }   
            return Double.toString(resultSet.getDouble("CA"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while calculating CA", e);
        }
    }

    @Override
    public List<Article> addProduct(Article article) throws RemoteException {
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        return addProducts(articles);
    }

    @Override
    public List<Article> addProduct(String type, double prix, int stock) throws RemoteException {
        Article article = new Article(null, prix, type, stock);
        return addProduct(article); 
    }

    @Override
    public List<Article> addProducts(List<Article> articles) throws RemoteException {
        try {
            List<PreparedStatement> statements = new ArrayList<>();
            for (Article article : articles) {
                String insertQuery = "INSERT INTO article (Type, Prix, Stock) VALUES ('" 
                + article.getType() + "', " 
                + article.getPrice() + ", " 
                + article.getStock() + ")";
                statements.add(context.ExecuteUpdate(insertQuery));
            }

            List<Article> createdArticles = new ArrayList<>();
            for (PreparedStatement preparedStatement : statements) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                long reference = generatedKeys.getLong(1);

                String query = "SELECT * FROM article WHERE Reference = " + reference;
                ResultSet resultSet = context.GetStatement(query);
                resultSet.next();
                Article createdArticle = new Article(
                    resultSet.getLong("Reference"),
                    resultSet.getDouble("Prix"),
                    resultSet.getString("Type"),
                    resultSet.getInt("Stock"));

                createdArticles.add(createdArticle);
            }

            if(createdArticles.size() != articles.size()) {
                throw new RemoteException("Error while adding products: " + articles.toString());
            }

            return createdArticles;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while adding products: " + articles.toString(), e);
        }
    }
}
