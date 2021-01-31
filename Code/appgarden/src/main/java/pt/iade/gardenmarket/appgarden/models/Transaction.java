package pt.iade.gardenmarket.appgarden.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transct_id")
    private int id;

    @OneToMany
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({"transaction"})
    private List<TransactionItem> transactionItems;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonIgnoreProperties({"transactions"})
    private User buyer;

    public Transaction() {}

    public int getId() {
        return id;
    }

    public List<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    public User getBuyer() {
        return buyer;
    }

    /*
    public float getTotalCost() {
        float totalcost = 0;
        return totalCost;
    }
    */
   
}
