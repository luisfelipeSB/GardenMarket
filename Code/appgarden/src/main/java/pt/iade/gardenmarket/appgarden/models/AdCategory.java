package pt.iade.gardenmarket.appgarden.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class AdCategory {       // Gostaria que isso fosse um Enum
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catg_id")
    private int id;

    @Column(name = "catg_name")
    private String name;

    public AdCategory() {}

    public AdCategory(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
