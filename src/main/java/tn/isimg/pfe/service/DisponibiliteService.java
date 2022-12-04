package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ExistException;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Disponibilite;
import tn.isimg.pfe.repository.DisponibiliteRepository;
import tn.isimg.pfe.repository.MedecinRepository;
import tn.isimg.pfe.repository.RendezVousRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibiliteService {

    @Autowired
    DisponibiliteRepository disponibiliteRepository;

    @Autowired
    MedecinRepository medecinRepository;

    @Autowired
    RendezVousRepository rendezVousRepository;

    // Get All Disponiblité d'un medecin by date
    public List<Disponibilite> findByIdAndDate(Long idMedecin, LocalDateTime dateDebut, LocalDateTime dateFin){
        return disponibiliteRepository.findByMedecinIdAndDateTimeBetween(idMedecin, dateDebut, dateFin);
    }

    // Get All Disponiblité d'un medecin
    public List<Disponibilite> findAllDisponibiliteByIdMedecin(Long idMedecin){
        return disponibiliteRepository.findByMedecinId(idMedecin);
    }

    // Get  Disponibilité By Id
    public Disponibilite findDisponibiliteById(Long idDisponibilite){
        return disponibiliteRepository.findById(idDisponibilite).
                orElseThrow(() -> new ResourceNotFoundException("id Disponibilité " + idDisponibilite + " not found"));
    }


    // get all disponibibilites d'un medecin sans rdv by date
    public List<Disponibilite> findByIdMedecinAndDateWithoutRdv(Long idMedecin, LocalDateTime dateDebut, LocalDateTime dateFin){
        List<Disponibilite> disponibilites=disponibiliteRepository
                .findByMedecinIdAndDateTimeBetween(idMedecin, dateDebut, dateFin);
        List<Disponibilite> disponibilites1=new ArrayList<>();

        for (Disponibilite disponibilite:disponibilites) {
            if (disponibilite.getRdv().equals(false)){
                disponibilites1.add(disponibilite);
            }

        }
        return disponibilites1;
    }

    // get all disponibibilites d'un medecin sans rdv
    public List<Disponibilite> findByIdMedecinWithoutRdv(Long idMedecin){
         List<Disponibilite> disponibilites=disponibiliteRepository
                 .findByMedecinId(idMedecin);
        List<Disponibilite> disponibilites1=new ArrayList<>();

        for (Disponibilite disponibilite:disponibilites) {
            if (disponibilite.getRdv().equals(false)){
                disponibilites1.add(disponibilite);
            }
            
        }
        return disponibilites1;
    }

//    //Creer Disponiblite By Id Medecin
//    private Disponibilite creer(Long idMedecin, Disponibilite disponibilite ) {
//        disponibiliteRepository.findByDateTime(disponibilite.getDateTime()).ifPresent(disponibilite1 -> {
//            throw new RuntimeException("disponibilite id : " +disponibilite.getId()+" est deja pris");
//        });
//
//        return medecinRepository.findById(idMedecin).map(medecin -> {
//            medecin.getDisponibilites().add(disponibilite);
//            disponibilite.setMedecin(medecin);
//            return disponibiliteRepository.save(disponibilite);
//        }).orElseThrow(() -> new ResourceNotFoundException("Compte Medecin " + idMedecin + " not found"));
//    }

    //Creer Disponiblite By Id Medecin
    private Disponibilite creerDisponibilite(Long idMedecin, Disponibilite disponibilite ) {
        disponibiliteRepository.findByMedecinIdAndDateTime(idMedecin,disponibilite.getDateTime()).ifPresent(disponibilite1 -> {
            throw new ExistException("disponibilite id : " +disponibilite.getId()+" est deja pris");
        });

        return medecinRepository.findById(idMedecin).map(medecin -> {
            disponibilite.setMedecin(medecin);
            return disponibiliteRepository.save(disponibilite);
        }).orElseThrow(() -> new ResourceNotFoundException("Compte Medecin " + idMedecin + " not found"));
    }

    public List<Disponibilite> creerlistDisponibilite(Long idMedecin , List<Disponibilite> disponibilites){
        return disponibilites
                .stream()
                .map(disponibilite -> creerDisponibilite(idMedecin,disponibilite))
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> deleteDisponibilite(Long idDisponibilite) {
        Disponibilite disponibilite= findDisponibiliteById(idDisponibilite);
        if (disponibilite.getRdv().equals(false)){
            disponibiliteRepository.delete(disponibilite);
            return ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("il faut annuler le rdv avant de supprimer diponibilite");
        }


    }

    public ResponseEntity<?> deleteByDate(Long idMedecin,LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Disponibilite> disponibilites = findByIdMedecinAndDateWithoutRdv(idMedecin, dateDebut, dateFin);


        for (Disponibilite disponibilite : disponibilites) {
            if (disponibilite.getRdv().equals(false)) {
                disponibiliteRepository.delete(disponibilite);
            }
        }

        return ResponseEntity.ok().build();
    }

    // get all disponibibilites d'un medecin sans rdv by date
    public List<Disponibilite> findByIdMedecinAndDateWithRdv(Long idMedecin, LocalDateTime dateDebut, LocalDateTime dateFin){
        List<Disponibilite> disponibilites=disponibiliteRepository
                .findByMedecinIdAndDateTimeBetween(idMedecin, dateDebut, dateFin);
        List<Disponibilite> disponibilites1=new ArrayList<>();

        for (Disponibilite disponibilite:disponibilites) {
            if (disponibilite.getRdv().equals(true)){
                disponibilites1.add(disponibilite);
            }

        }
        return disponibilites1;
    }

}
