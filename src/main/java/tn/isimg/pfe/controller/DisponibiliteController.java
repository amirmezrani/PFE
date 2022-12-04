package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.Disponibilite;
import tn.isimg.pfe.service.DisponibiliteService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class DisponibiliteController {


    @Autowired
    DisponibiliteService disponibiliteService;

    // Get  Disponibilité By Id
    @GetMapping("/medecins/disponibilites/{id}")
    public Disponibilite findDisponibiliteById(@PathVariable(value = "id") Long id){
        return disponibiliteService.findDisponibiliteById(id);
    }

    // Get  Disponibilité By Id pour le patient
    @GetMapping("/comptePatients/disponibilites/{id}")
    public Disponibilite findDisponibiliteByIdForPatient(@PathVariable(value = "id") Long id){
        return disponibiliteService.findDisponibiliteById(id);
    }

      // Get All Disponiblité d'un medecin by Date
    @GetMapping("/medecins/{idMedecin}/disponibilites")
    public List<Disponibilite> findAllDisponibliteByDate(@PathVariable(value = "idMedecin") Long idMedecin,
                                                   @RequestParam(value = "dateDebut") LocalDateTime dateDebut,
                                                   @RequestParam(value = "dateFin") LocalDateTime dateFin){
        return disponibiliteService.findByIdAndDate(idMedecin, dateDebut, dateFin);
    }

    // Get All Disponiblité d'un medecin
    @GetMapping("/medecins/{idMedecin}/disponibilitesAll")
    public List<Disponibilite> findAllDisponiblite(@PathVariable(value = "idMedecin") Long idMedecin){
        return disponibiliteService.findAllDisponibiliteByIdMedecin(idMedecin);
    }

    // get all disponibibilites d'un medecin sans rdv by date pour le patient
    @GetMapping("/comptePatients/medecins/{idMedecin}/disponibilitesVideByDate")
    public List<Disponibilite> findAllDisponibliteWithoutRdvForPatient(@PathVariable(value = "idMedecin") Long idMedecin,
                                                             @RequestParam(value = "dateDebut") LocalDateTime dateDebut,
                                                             @RequestParam(value = "dateFin") LocalDateTime dateFin){
        return disponibiliteService.findByIdMedecinAndDateWithoutRdv(idMedecin, dateDebut, dateFin);
    }

    // get all disponibibilites d'un medecin sans rdv
    @GetMapping("/disponibilitesVideUnauthorized/medecins/{idMedecin}")
    public List<Disponibilite> findAllDisponibliteWithoutRdv(@PathVariable(value = "idMedecin") Long idMedecin){
        return disponibiliteService.findByIdMedecinWithoutRdv(idMedecin);
    }

    // get all disponibibilites d'un medecin sans rdv by date
    @GetMapping("/disponibilitesVideUnauthorizedByDate/medecins/{idMedecin}")
    public List<Disponibilite> findAllDisponibliteByMedecinWithoutRdv(@PathVariable(value = "idMedecin") Long idMedecin,
                                                             @RequestParam(value = "dateDebut") LocalDateTime dateDebut,
                                                             @RequestParam(value = "dateFin") LocalDateTime dateFin){
        return disponibiliteService.findByIdMedecinAndDateWithoutRdv(idMedecin, dateDebut, dateFin);
    }

    // get all disponibibilites d'un medecin sans rdv pour le patient
    @GetMapping("/comptePatients/medecins/{idMedecin}/disponibilitesVide")
    public List<Disponibilite> findAllDisponibliteWithoutRdvForPatient(@PathVariable(value = "idMedecin") Long idMedecin){
        return disponibiliteService.findByIdMedecinWithoutRdv(idMedecin);
    }

    //Creer Disponiblite By Id Medecin
    @PostMapping("/medecins/{idMedecin}/disponibilites")
    public List<Disponibilite> creerDisponibilite(@PathVariable (value = "idMedecin") Long idMedecin,
                                      @Valid @RequestBody List<Disponibilite> disponibilites ) {
            return disponibiliteService.creerlistDisponibilite(idMedecin,disponibilites);
    }

    @DeleteMapping("/medecins/disponibilites/{id}")
    public ResponseEntity<?> deleteDisponibilite(@PathVariable (value = "id") Long id) {
            return disponibiliteService.deleteDisponibilite(id);
    }

    @DeleteMapping("/medecins/{idMedecin}/disponibilites")
    public ResponseEntity<?> deleteDisponibiliteByDate(@PathVariable(value = "idMedecin") Long idMedecin,
                                                       @RequestParam(value = "dateDebut") LocalDateTime dateDebut,
                                                       @RequestParam(value = "dateFin") LocalDateTime dateFin) {
        return disponibiliteService.deleteByDate(idMedecin, dateDebut, dateFin);
    }
}
