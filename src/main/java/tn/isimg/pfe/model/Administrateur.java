package tn.isimg.pfe.model;

import javax.persistence.*;

@Entity
@Table(name = "administrateur")
public class Administrateur extends Utlisateur{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
