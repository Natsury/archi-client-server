package fr.siege;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import fr.siege.datatype.ArticleFacture;
import fr.siege.datatype.Facture;
import fr.siege.interfaces.IHeptathlon_siege;

public class Heptathlon_siege implements IHeptathlon_siege{
    private Context context;

    public Heptathlon_siege() {
        this.context = Context.getInstance();
    }

    @Override
    public void updateStock(Long reference, int newStock) throws RemoteException {
        try {
            String query = "UPDATE article SET stock = " + newStock + " WHERE reference = " + reference;
            context.ExecuteUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to update stock: " + e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @Override
    public void updatePrice(Long reference, double newPrice) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePrice'");
    }

    @Override
    public void updatePrices() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePrices'");
    }

    @Override
    public void saveBills(List<Facture> factures) throws RemoteException {
        try {
            for (Facture facture : factures) {
                this.saveBill(facture);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save bills: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }    

    @Override
    public void saveBill(Facture facture) throws RemoteException {
        try {
            String query = "INSERT INTO facture (Date_fac, Prix_total)" 
            + " VALUES ('" + LocalDate.parse(facture.getDate_fac()) + "', " + facture.getPrix_total() + ")";
            PreparedStatement stmt = context.ExecuteUpdate(query);

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int factureId = generatedKeys.getInt(1);
                for (ArticleFacture article : facture.getArticles()) {
                    String articleQuery = "INSERT INTO Panier (num_facture, reference, quantite)" 
                    + " VALUES (" + factureId + ", " 
                    + article.getReference() + ", " 
                    + article.getQuantity() + ")";

                    context.ExecuteUpdate(articleQuery);
                    updateStock(article.getReference(), article.getStock() - article.getQuantity());
                }
            } else {
                throw new RuntimeException("Failed to retrieve generated facture ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save bill: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
