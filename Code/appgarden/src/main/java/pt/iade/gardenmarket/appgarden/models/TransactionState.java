package pt.iade.gardenmarket.appgarden.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class TransactionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ts_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "transct_id")
    @JsonIgnoreProperties({"transactionStates"})
    private Transaction transaction; 

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state; 

    @Column(name = "ts_date")
    private Date date;

    public TransactionState() {}

}