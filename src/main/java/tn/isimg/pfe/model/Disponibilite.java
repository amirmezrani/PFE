package tn.isimg.pfe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "disponibilite")
public class Disponibilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateTime", nullable = false)
    @JsonFormat(pattern = "yyyy'-'MM'-'dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Column(name = "rdv", nullable = true)
    private Boolean rdv=false;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "medecin_id",nullable = false)
    Medecin medecin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) { this.medecin = medecin; }

    public Boolean getRdv() {
        return rdv;
    }

    public void setRdv(Boolean rdv) {
        this.rdv = rdv;
    }
}
