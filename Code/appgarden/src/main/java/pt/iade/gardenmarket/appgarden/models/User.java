package pt.iade.gardenmarket.appgarden.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private int id;

    @Column(name = "usr_name")
    private String name;

    @Column(name = "usr_password")
    private String password;

    @Column(name = "usr_email")
    private String email;

    @OneToMany
    @JoinColumn(name = "sellr_id")
    @JsonIgnoreProperties({"seller"})
    private List<Advertisement> ads;

    @OneToMany
    @JoinColumn(name = "buyer_id")
    @JsonIgnoreProperties({"buyer"})
    private List<TransactionItem> transactionItems;

    public User() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Advertisement> getAds() {
        return ads;
    }

    public List<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    /*
    public List<TransactionItem> getWishlist() {
        ArrayList<TransactionItem> wishlist = new ArrayList<>();
        for (TransactionItem item : transactionItems)
            if (item.getId() == null) wishlist.add(item);
        return wishlist;
    }
    */

    /*
    public List<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (TransactionItem item : transactionItems)
            if (item.getId() != null) transactions.add(item);
        return transactions;
    }
    */

}
