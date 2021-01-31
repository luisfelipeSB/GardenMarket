package pt.iade.gardenmarket.appgarden.models;

import java.util.ArrayList;
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
    private List<Advertisement> ads = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "transct_id")
    @JsonIgnoreProperties({"buyer"})
    private List<Transaction> transactions;

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction t) {
        transactions.add(t);
    }

}
