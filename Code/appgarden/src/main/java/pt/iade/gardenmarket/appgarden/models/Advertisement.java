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
@Table(name = "advertisements")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "sellr_id")
    @JsonIgnoreProperties({ "ads" })
    private User seller;

    @ManyToOne
    @JoinColumn(name = "catg_id")
    private AdCategory category;

    @Column(name = "ad_title")
    private String title;

    @Column(name = "ad_description")
    private String description;

    @Column(name = "ad_price")
    private float price;

    @Column(name = "ad_isactive")
    private boolean isActive;

    public Advertisement() {}

    public Advertisement(String category, String title, String description, float price) {
        this.category = new AdCategory(category); // Isso não devia de ser assim
        this.title = title;
        this.description = description;
        this.price = price;
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public AdCategory getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isSold) {
        this.isActive = isSold;
    }
}
