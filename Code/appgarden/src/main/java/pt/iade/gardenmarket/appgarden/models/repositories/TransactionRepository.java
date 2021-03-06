package pt.iade.gardenmarket.appgarden.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import pt.iade.gardenmarket.appgarden.models.Transaction;
import pt.iade.gardenmarket.appgarden.models.views.AdSummaryView;
import pt.iade.gardenmarket.appgarden.models.views.PurchasedAdSummaryView;
import pt.iade.gardenmarket.appgarden.models.views.TransactionStateView;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    // Creating a new cart transaction
    String createCartQuery = 
    "CALL create_cart(:userId) ";
    @Modifying
    @Transactional
    @Query(value=createCartQuery, nativeQuery=true)
    int createCart(@Param("userId") int userId);
    
    // Updating a transaction's state
    String updateTransactionStateQuery =
    "CALL update_ts(:transctId, :stateId) ";
    @Modifying
    @Transactional
    @Query(value=updateTransactionStateQuery, nativeQuery=true)
    int updateTS(@Param("transctId") int transctId, @Param("stateId") int stateId);

    // Adding an ad to a cart given transaction and ad ids
    String addToCartQuery =
    "INSERT INTO transactionitems (transct_id, ad_id) VALUES (:transctId, :adId)";
    @Modifying
    @Transactional
    @Query(value=addToCartQuery, nativeQuery=true)
    int addToCart(@Param("transctId") int transctId, @Param("adId") int adId);

    // Removing an item from a cart given transaction and ad ids
    String removeFromCartQuery =
    "DELETE FROM transactionitems WHERE transct_id = :transctId AND ad_id = :adId";
    @Modifying
    @Transactional
    @Query(value=removeFromCartQuery, nativeQuery=true)
    int removeFromCart(@Param("transctId") int transctId, @Param("adId") int adId); 


    /*----- TRANSACTION STATE QUERIES -----*/

    // String for a TransactionStateView
    String transctStateViewQuery =
    "SELECT t.transct_id as transactionId, t.buyer_id as buyerId, max(ts.state_id) as statelvl FROM transactions t " +
    "INNER JOIN users u on usr_id = t.buyer_id " +
    "INNER JOIN transactionstate ts on ts.transct_id = t.transct_id ";

    // Getting a TransactionStateView given transaction id
    String stateViewGivenTransactionQuery = transctStateViewQuery +
    "WHERE t.transct_id = :transctId ";
    @Transactional
    @Query(value=stateViewGivenTransactionQuery, nativeQuery=true)
    TransactionStateView getStateView(@Param("transctId") int transctId);

    // Getting current states of a all of a user's transactions
    String allUserTransactionStatesQuery = transctStateViewQuery + 
    "WHERE usr_id = :userId " + 
    "GROUP BY t.transct_id ";
    @Transactional
    @Query(value=allUserTransactionStatesQuery, nativeQuery=true)
    TransactionStateView getUserTransctStatesView(@Param("userId") int userId);

    // Getting a user's cart transaction, if they have one
    String userCartQuery = 
    "SELECT * FROM (" +  allUserTransactionStatesQuery + ") a " +
    "WHERE statelvl = 1 LIMIT 1 ";
    @Transactional
    @Query(value=userCartQuery, nativeQuery=true)
    Optional<TransactionStateView> getUserCartView(@Param("userId") int userId);


    /*----- TRANSACTIONITEMS QUERIES -----*/

    String AdSummariesQuery = 
    "SELECT a.ad_id AS id, ad_title AS title, usr_name AS seller, ad_price AS price, catg_name AS category " +
    "FROM advertisements a " +
    "INNER JOIN users ON usr_id = a.sellr_id " +
    "INNER JOIN adcategories c ON c.catg_id = a.catg_id " +
    "INNER JOIN transactionitems ts ON ts.ad_id = a.ad_id " +
    "INNER JOIN transactions t ON t.transct_id = ts.transct_id ";

    String cartAdSummariesQuery = AdSummariesQuery +
    "WHERE t.buyer_id = :buyerId AND a.ad_isactive = true ";
    @Query(value = cartAdSummariesQuery, nativeQuery = true)
    Iterable<AdSummaryView> getUserCartItems(@Param("buyerId") int buyerId);

    String purchasesQuery = AdSummariesQuery +
    "WHERE t.buyer_id = :buyerId AND a.ad_isactive = false ";
    @Query(value = purchasesQuery, nativeQuery = true)
    Iterable<AdSummaryView> getUserPurchaseItems(@Param("buyerId") int buyerId);

    String PurchasedAdSummariesQuery = 
    "SELECT t.transct_id AS transactionId, ts_date AS purchaseDate, s.state_name AS state, " + 
            "a.ad_id AS id, a.ad_title AS title, u.usr_name AS buyer, a.sellr_id AS seller, "+
            "a.ad_price AS price, c.catg_name AS category " +
    "FROM advertisements a " +
    "INNER JOIN adcategories c ON a.catg_id = c.catg_id " +
    "INNER JOIN transactionitems ti ON ti.ad_id = a.ad_id " +
    "INNER JOIN transactions t ON t.transct_id = ti.transct_id " +
    "INNER JOIN transactionstate ts ON ts.transct_id = t.transct_id " +
    "INNER JOIN state s ON s.state_id = ts.state_id " +
    "INNER JOIN users u ON u.usr_id = t.buyer_id " +
    "WHERE u.usr_id = :buyerId AND ts.state_id BETWEEN 2 AND 4 ";
    @Query(value = PurchasedAdSummariesQuery, nativeQuery = true)
    Iterable<PurchasedAdSummaryView> getUserPurchasedItems(@Param("buyerId") int buyerId);
}
