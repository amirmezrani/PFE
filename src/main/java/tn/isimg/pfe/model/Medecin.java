package tn.isimg.pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medecin")
public class Medecin extends Utlisateur{



    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "presentation", nullable = false)
    private String presentation;

    @Column(name = "contact", nullable = false,unique = true)
    private String conact;

    @Column(name = "contact_urgence", nullable = false)
    private String contactUrgence;

    @Column(name = "valider", nullable = true)
    private Boolean valider=false;

    @Column(name = "rejeter", nullable = true)
    private Boolean rejeter=false;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "ville_id",nullable = false)
    Ville ville ;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "specialite_id",nullable = false)
    Specialite specialite ;

//    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    @JsonIgnore
//    Set<Disponibilite> disponibilites=new HashSet<>();


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }



    public String getContactUrgence() {
        return contactUrgence;
    }

    public void setContactUrgence(String contactUrgence) {
        this.contactUrgence = contactUrgence;
    }

    public String getConact() {
        return conact;
    }

    public void setConact(String conact) {
        this.conact = conact;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Boolean getValider() {
        return valider;
    }

    public void setValider(Boolean valider) {
        this.valider = valider;
    }

    public Boolean getRejeter() {
        return rejeter;
    }

    public void setRejeter(Boolean rejeter) {
        this.rejeter = rejeter;
    }
}
