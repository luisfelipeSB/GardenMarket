package pt.iade.gardenmarket.appgarden.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.gardenmarket.appgarden.models.User;
import pt.iade.gardenmarket.appgarden.models.exceptions.NotFoundException;
import pt.iade.gardenmarket.appgarden.models.repositories.TransactionRepository;
import pt.iade.gardenmarket.appgarden.models.repositories.UserRepository;
import pt.iade.gardenmarket.appgarden.models.views.AdSummaryView;
import pt.iade.gardenmarket.appgarden.models.views.TransactionStateView;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transctRepository;

    // Get all users
    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> getUsers() {
        logger.info("Sending all users");
        return userRepository.findAll();
    }

    // Get one user
    @GetMapping(path = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable int id) {
        logger.info("Sending user with id "+id);
        Optional<User> _user = userRepository.findById(id);
        if (_user.isEmpty()) throw new NotFoundException(""+id,"user","id");
        else return _user.get() ;
    }

    // Creating a new shopping transaction for the user, then setting it to cart state
    @PostMapping(path = "{id}/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public int createCart(@PathVariable int id) {
        return transctRepository.createCart(id);
    }

    // Get one user's cart as TransactionStateView, if they have one
    @GetMapping(path = "{id}/cart", produces = MediaType.APPLICATION_JSON_VALUE)  // THIS MAY RETURN NULL
    public Optional<TransactionStateView> getUserCart(@PathVariable("id") int id) {
        logger.info("Sending a view of user " + id + "'s cart");
        Optional<TransactionStateView> cartView = transctRepository.getUserCartView(id);
        if (cartView.isEmpty()) throw new NotFoundException("" + id, "transct", "id");
        else return cartView;
    }

    // Get one user's cart items
    @GetMapping(path = "{id}/cartItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> getUserCartItems(@PathVariable("id") int id) {
        logger.info("Sending a view of user " + id + "'s cart items");
        Iterable<AdSummaryView> cartItems = transctRepository.getUserCartItems(id);
        return cartItems;
    }

    // Getting one user's purchased items
    @GetMapping(path = "{id}/purchaseItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> getUserPurchaseItems(@PathVariable("id") int id) {
        logger.info("Sending a view of user " + id + "'s purchase items");
        Iterable<AdSummaryView> purchases = transctRepository.getUserPurchaseItems(id);
        return purchases;
    }
}
