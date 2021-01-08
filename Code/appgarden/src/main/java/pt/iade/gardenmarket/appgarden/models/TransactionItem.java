package pt.iade.gardenmarket.appgarden.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "transactionitems")
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "transct_id")
    @JsonIgnoreProperties({"transactionItems"})
    private Transaction transaction;  
    
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonIgnoreProperties({"transactionItems", "ads"})
    private User buyer;  
    
    @ManyToOne
    @JoinColumn(name = "ad_id")
    @JsonIgnoreProperties({"seller"})
    private Advertisement ad;  

    public TransactionItem() {}

    public int getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public User getBuyer() {
        return buyer;
    }

    public Advertisement getAd() {
        return ad;
    }
    
}
