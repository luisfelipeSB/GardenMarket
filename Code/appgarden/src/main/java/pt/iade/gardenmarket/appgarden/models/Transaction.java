package pt.iade.gardenmarket.appgarden.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transct_id")
    private int id;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<TransactionItem> transactionItems;     // JsonIgnore comprador e ads? Seria informação desnecessária

    @Column(name = "transct_total")
    private float totalCost;

    @Column(name = "transct_paymentdate")
    private Date purchaseDate;

    @Column(name = "transct_dispatchdate")
    private Date dispatchDate;

    @Column(name = "transct_deliverydate")
    private Date deliveryDate;

    public Transaction() {}

    public int getId() {
        return id;
    }

    public List<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
   
}
