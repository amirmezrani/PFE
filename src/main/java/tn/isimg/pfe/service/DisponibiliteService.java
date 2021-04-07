package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Disponibilite;
import tn.isimg.pfe.repository.DisponibiliteRepository;
import tn.isimg.pfe.repository.MedecinRepository;

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

    // Get All Disponiblité d'un medecin
    public List<Disponibilite> findByIdAndDate(Long id, LocalDateTime dateDebut, LocalDateTime dateFin){
        return disponibiliteRepository.findByMedecinIdAndDateTimeBetween(id, dateDebut, dateFin);
    }

    // Get  Disponibilité By Id
    public Disponibilite findDisponibilite(Long idDisponibilite){
        return disponibiliteRepository.findById(idDisponibilite).
                orElseThrow(() -> new ResourceNotFoundException("id Disponibilité " + idDisponibilite + " not found"));
    }


    // get all disponibibilites sans rdv
    public List<Disponibilite> findByIdAndDateWithoutRdv(Long id, LocalDateTime dateDebut, LocalDateTime dateFin){
         List<Disponibilite> disponibilites=disponibiliteRepository
                 .findByMedecinIdAndDateTimeBetween(id, dateDebut, dateFin);
        List<Disponibilite> disponibilites1=new ArrayList<>();

        for (Disponibilite disponibilite:disponibilites) {
            if (disponibilite.getRdv().equals(false)){
                disponibilites1.add(disponibilite);
            }
            
        }
        return disponibilites1;
    }

    //Creer Disponiblite By Id Medecin
    private Disponibilite creer(Long idMedecin, Disponibilite disponibilite ) {
        disponibiliteRepository.findByDateTime(disponibilite.getDateTime()).ifPresent(disponibilite1 -> {
            throw new RuntimeException("disponibilite id : " +disponibilite.getId()+" est deja pris");
        });

        return medecinRepository.findById(idMedecin).map(medecin -> {
            medecin.getDisponibilites().add(disponibilite);
            disponibilite.setMedecin(medecin);
            return disponibiliteRepository.save(disponibilite);
        }).orElseThrow(() -> new ResourceNotFoundException("Compte Medecin " + idMedecin + " not found"));
    }

    public List<Disponibilite> creerlist(Long idMedecin , List<Disponibilite> disponibilites){
        return disponibilites
                .stream()
                .map(disponibilite -> creer(idMedecin,disponibilite))
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> delete(Long idDisponibilite) {
        return disponibiliteRepository.findById(idDisponibilite).map(disponibilite -> {
            disponibiliteRepository.delete(disponibilite);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("disponibilité not found with id " + idDisponibilite));
    }
}
