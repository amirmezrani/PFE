package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.Disponibilite;
import tn.isimg.pfe.service.DisponibiliteService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DisponibiliteController {


    @Autowired
    DisponibiliteService disponibiliteService;

      // Get All Disponiblité d'un medecin
    @GetMapping("/medecins/{id}/disponibilites")
    public List<Disponibilite> findAllDisponiblite(@PathVariable(value = "id") Long id,
                                                   @RequestParam(value = "dateDebut") LocalDateTime dateDebut,
                                                   @RequestParam(value = "dateFin") LocalDateTime dateFin){
        return disponibiliteService.findByIdAndDate(id, dateDebut, dateFin);
    }

    // Get All Disponiblité d'un medecin without rdv
    @GetMapping("/medecins/{id}/disponibilitesVide")
    public List<Disponibilite> findAllDisponibliteWithoutRdv(@PathVariable(value = "id") Long id,
                                                             @RequestParam(value = "dateDebut") LocalDateTime dateDebut,
                                                             @RequestParam(value = "dateFin") LocalDateTime dateFin){
        return disponibiliteService.findByIdAndDateWithoutRdv(id, dateDebut, dateFin);
    }

    // Get  Disponibilité By Id
    @GetMapping("/disponibilites/{id}")
    public Disponibilite findDisponibiliteById(@PathVariable(value = "id") Long id){
        return disponibiliteService.findDisponibilite(id);
    }

    //Creer Disponiblite By Id Medecin
    @PostMapping("/medecins/{id}/disponibilites")
    public List<Disponibilite> creerDisponibilite(@PathVariable (value = "id") Long id,
                                      @Valid @RequestBody List<Disponibilite> disponibilites ) {
            return disponibiliteService.creerlist(id,disponibilites);
    }

   /* @PutMapping("/diponibilites/{id}")
    public Disponibilite updatediponibilite(@PathVariable (value = "id") Long id,
                                 @Valid @RequestBody Disponibilite disponibiliteRequest ){

        return disponibiliteRepository.findById(id).map(disponibilite -> {
            disponibilite.setDateTime(disponibilite.getDateTime());
            return disponibiliteRepository.save(disponibilite);
        }).orElseThrow(() -> new ResourceNotFoundException("id Disponibilité " + id + "not found"));

    }*/


    public ResponseEntity<?> deleteDisponibilite(@PathVariable (value = "id") Long id) {
            return disponibiliteService.delete(id);
    }
}
