package pt.iade.gardenmarket.appgarden.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.gardenmarket.appgarden.models.Advertisement;
import pt.iade.gardenmarket.appgarden.models.Transaction;
import pt.iade.gardenmarket.appgarden.models.TransactionState;
import pt.iade.gardenmarket.appgarden.models.exceptions.NotFoundException;
import pt.iade.gardenmarket.appgarden.models.repositories.TransactionRepository;
import pt.iade.gardenmarket.appgarden.models.repositories.UserRepository;
import pt.iade.gardenmarket.appgarden.models.views.AdSummaryView;
import pt.iade.gardenmarket.appgarden.models.views.StatelessTransactionView;
import pt.iade.gardenmarket.appgarden.models.views.TransactionStateView;

@RestController
@RequestMapping(path="/api/transactions")
public class TransactionController {

    private Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionRepository transctRepository;
    
    // Get all transactions
    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Transaction> getTransactions() {
        logger.info("Sending all transactions");
        return transctRepository.findAll();
    }

    // Get one transaction
    @GetMapping(path = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Transaction getTransaction(@PathVariable int id) {
        logger.info("Sending transaction with id " + id);
        Optional<Transaction> _transct = transctRepository.findById(id);
        if (_transct.isEmpty()) throw new NotFoundException(""+id,"transct","id");
        else return _transct.get() ;
    }

    // Creating a new shopping transaction, then setting it to cart state
    @PostMapping(path = "/createCart/{uId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int createCart(@RequestBody int userId, @PathVariable int uId) {
        return transctRepository.createCart(uId);
    }

    /* Creating a new shopping transaction, then setting it to cart state
    // The code in here needs to execute atomically
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public int createCart(@RequestBody int userId) {
        logger.info("Saving a new transaction in cart state for user with id " + userId);
        // Creating that transaction
        int newTransct = transctRepository.createTransaction(userId);
        // Getting that newly made transaction
        int newTransctId = getStatelessTransaction(userId);   
        // Registering a cart state entry in transactionStates for the new transaction
        int newTS = transctRepository.setToCartState(newTransctId);
        return newTransct;
    }
    // Getting one user's transaction(s) without an associated state
    @GetMapping(path = "/stateless/user/{userId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public int getStatelessTransaction(@PathVariable int userId) {
        logger.info("Sending stateless transaction from user with id " + userId);
        StatelessTransactionView _transct = transctRepository.getStatelessTransaction(userId);
        return _transct.getTransactionId();
    }
    */

    // Adding an item to a cart transaction
    // (creating a transactionItem and associating it with a transaction in cart state)
    @PostMapping(path = "/{id}/addToCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public int addToCart(@PathVariable int id, @RequestBody Advertisement ad) {
        logger.info("Saving ad: " + ad + " to cart: " + id);
        logger.info(ad.toString());

        // Verifying that the transaction received is in cart state
        if (getTransactionCurrentState(id).getStateLvl() == 1)
            // Adding item to cart
            return transctRepository.addToCart(id, ad);
        else
            return 132;
    }

    // Getting a view of a transaction's current state
    @GetMapping(path = "/{id}/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionStateView getTransactionCurrentState(@PathVariable("id") int id) {    // THIS MAY RETURN NULL
        logger.info("Sending a view of a transaction " + id + "'s' current state");
        return transctRepository.getStateView(id);
    }

    // Getting a view of a user's cart transaction, if they have one
    @GetMapping(path = "/user/{userId}/cart", produces = MediaType.APPLICATION_JSON_VALUE)  // THIS MAY RETURN NULL
    public Optional<TransactionStateView> getUserCart(@PathVariable("userId") int userId) {
        logger.info("Sending a view of user " + userId + "'s cart");
        Optional<TransactionStateView> cartView = transctRepository.getUserCartView(userId);
        logger.info(cartView.toString());
        return cartView;
    }

    // Getting one user's cart transactionItems in AdSummaryView
    @GetMapping(path = "/user/{userId}/cartItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> getUserCartItems(@PathVariable("userId") int userId) {
        logger.info("Sending a view of user " + userId + "'s cart items");
        Iterable<AdSummaryView> cartItems = transctRepository.getUserCartItems(userId);
        return cartItems;
    }

    // Updating a transactionstate
    @PostMapping(path = "/update/{statelvl}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int updateTS(@RequestBody int transctId, @PathVariable("statelvl") int statelvl) {
        logger.info("Updating transaction " + transctId + "to purchased state");
        return transctRepository.updateTS(transctId, statelvl);
    }
}
