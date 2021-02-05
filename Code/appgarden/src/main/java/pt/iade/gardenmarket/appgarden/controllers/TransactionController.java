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
import pt.iade.gardenmarket.appgarden.models.exceptions.NotFoundException;
import pt.iade.gardenmarket.appgarden.models.repositories.TransactionRepository;
import pt.iade.gardenmarket.appgarden.models.views.AdSummaryView;
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

    // Adding an item to a cart transaction
    // 1) Verifying whether an ad can be added to a user's cart and
    // 2) creating a transactionItem and associating it with a transaction in cart state
    @PostMapping(path = "/cart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int addToCart(@PathVariable int id, @RequestBody Advertisement ad) {
        TransactionStateView cart = getTransactionCurrentState(id);
        logger.info("Saving ad: " + ad.getId() + " to cart: " + id);

        // Checking if the ad is active, if the user is not also the seller, and if the transaction is in cart state
        if (ad.isActive() && ad.getSeller().getId() != cart.getBuyerId() && cart.getStateLvl() == 1) {    
            
            // Checking if the ad is in the user's cart already
            for (AdSummaryView item : transctRepository.getUserCartItems(cart.getBuyerId())) 
                if (ad.getId() == item.getId()) return 0;
            
            // Adding item to cart
            logger.info("Success");
            return transctRepository.addToCart(id, ad.getId());
        } else {
            return 0;
        }
    }

    // Getting a view of a transaction's current state
    @GetMapping(path = "/{id}/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionStateView getTransactionCurrentState(@PathVariable("id") int id) {    // THIS MAY RETURN NULL
        logger.info("Sending a view of a transaction " + id + "'s' current state");
        return transctRepository.getStateView(id);
    }

    // Updating a transactionstate
    @PostMapping(path = "/update/{statelvl}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int updateTS(@RequestBody int transctId, @PathVariable("statelvl") int statelvl) {
        logger.info("Updating transaction " + transctId + "to purchased state");
        return transctRepository.updateTS(transctId, statelvl);
    }
}
