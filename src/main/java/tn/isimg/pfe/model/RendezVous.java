package tn.isimg.pfe.model;

import javax.persistence.*;

@Entity
@Table(name = "rendezvous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "patient_id",nullable = false)
    Patient patient  ;

    @OneToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "disponibilte",nullable = false)
    Disponibilite disponibilite  ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }
}
