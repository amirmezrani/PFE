package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.RendezVous;
import tn.isimg.pfe.service.RendezVousService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RendezVousController {

    @Autowired
    RendezVousService rendezVousService;

    // Get  Rendez-Vous By Id pour medecin
    @GetMapping("/medecins/rendezvous/{id}")
    public RendezVous findRendezVous(@PathVariable(value = "id") Long id){
        return rendezVousService.findById(id);
    }

    // Get  Rendez-Vous By Id pour le patient
    @GetMapping("/comptePatients/rendezvous/{id}")
    public RendezVous findRendezVousForPatient(@PathVariable(value = "id") Long id){
        return rendezVousService.findById(id);
    }



    // Get All Rendez-Vous d'un patient
    @GetMapping("/comptePatients/patients/{idpatient}/rendezvous")
    public List<RendezVous> findAllRendezVousByPatient(@PathVariable(value = "idpatient") Long idpatient){
        return rendezVousService.findAllRdvByIdPatient(idpatient);
    }

    // Get All Rendez-Vous d'un medecin
    @GetMapping("/medecins/{idmedecin}/rendezvous")
    public List<RendezVous> findAllRendezVousByMedecin(@PathVariable(value = "idmedecin") Long idmedecin){
        return rendezVousService.findAllRdvByIdMedecin(idmedecin);
    }

    // Get All Rendez-Vous d'un patient
    @GetMapping("/comptePatients/{idcomptePatient}/rendezvous")
    public List<RendezVous> findAllRendezVousByComptePatient(@PathVariable(value = "idcomptePatient") Long idcomptePatient){
        return rendezVousService.findAllRdvByIdComptePatient(idcomptePatient);
    }

    //Creer Rendez-Vous
    @PostMapping("/comptePatients/rendezvous")
    public RendezVous creerRendezVous(@Valid @RequestBody RendezVous rendezVous ) {
        return rendezVousService.creerRDV(rendezVous);
  }

    // Get  Rendez-Vous By Id

    @GetMapping("/medecins/disponibilites/idDisponibilite")
    public RendezVous findRendezVousByDisponibilite(@PathVariable(value = "idDisponibilite") Long idDisponibilite){
        return rendezVousService.findRDVByIdDisponibilite(idDisponibilite);
    }

    // Update  Rendez-Vous By Id pour medecin
    @PutMapping("/medecins/rendezvous/{id}")
    public RendezVous updateRendezVous(@PathVariable (value = "id") Long id,
                                 @Valid @RequestBody RendezVous rendezVous){
        return rendezVousService.updateRDV(id, rendezVous);
    }

    // Get  Rendez-Vous By Id pour patient
    @PutMapping("/comptePatients/rendezvous/{id}")
    public RendezVous updateRendezVousForPatient(@PathVariable (value = "id") Long id,
                                       @Valid @RequestBody RendezVous rendezVous){
        return rendezVousService.updateRDV(id, rendezVous);
    }

    // Delete  Rendez-Vous By Id pour medecin
    @DeleteMapping("/medecins/rendezvous/{id}")
    public ResponseEntity<?> deleteRendezVous(@PathVariable (value = "id") Long id) {
        return rendezVousService.deleteRDV(id);
    }
    // Delete  Rendez-Vous By Id pour patient
    @DeleteMapping("/comptePatients/rendezvous/{id}")
    public ResponseEntity<?> deleteRendezVousForPatient(@PathVariable (value = "id") Long id) {
        return rendezVousService.deleteRDV(id);
    }

    @DeleteMapping("/medecins/{idMedecin}/rendezvous")
    public ResponseEntity<?> deleteDisponibiliteByDate(@PathVariable(value = "idMedecin") Long idMedecin,
                                                       @RequestParam(value = "dateDebut") LocalDateTime dateDebut,
                                                       @RequestParam(value = "dateFin") LocalDateTime dateFin) {
        return rendezVousService.deleteRDVByIdMedecinDate(idMedecin, dateDebut, dateFin);
    }
}
