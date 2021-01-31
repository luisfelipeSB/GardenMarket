package pt.iade.gardenmarket.appgarden.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "states")
public class State {       // Gostaria que isso fosse um Enum
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private int id;

    @Column(name = "state_name")
    private String name;

    public State() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
