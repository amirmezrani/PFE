package tn.isimg.pfe.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comptePatient")
public class ComptePatient extends Utlisateur{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JsonIgnore
    Set<Patient> membresFamille=new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false)
    Patient patientPrincipal;




    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Patient> getMembresFamille() {
        return membresFamille;
    }

    public void setMembresFamille(Set<Patient> membresFamille) {
        this.membresFamille = membresFamille;
    }

    public Patient getPatientPrincipal() {
        return patientPrincipal;
    }

    public void setPatientPrincipal(Patient patientPrincipal) {
        this.patientPrincipal = patientPrincipal;
    }
}

