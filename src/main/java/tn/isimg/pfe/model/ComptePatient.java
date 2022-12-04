package tn.isimg.pfe.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comptePatient")
public class ComptePatient extends Utlisateur{


    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "comptePatient")
    @JsonIgnore
    Set<Patient> membresFamille=new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false)
    Patient patientPrincipal;




    // Getters and Setters


    public Patient getPatientPrincipal() {
        return patientPrincipal;
    }

    public void setPatientPrincipal(Patient patientPrincipal) {
        this.patientPrincipal = patientPrincipal;
    }


}

