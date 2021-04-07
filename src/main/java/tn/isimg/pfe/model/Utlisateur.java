package tn.isimg.pfe.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


@MappedSuperclass
public abstract class Utlisateur implements Serializable {

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "motDePasse", nullable = false)
    private String motDePasse;

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
