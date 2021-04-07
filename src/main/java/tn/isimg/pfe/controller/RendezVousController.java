package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.RendezVous;
import tn.isimg.pfe.service.RendezVousService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RendezVousController {

    @Autowired
    RendezVousService rendezVousService;

    // Get  Rendez-Vous By Id
    @GetMapping("/rendezvous/{id}")
    public RendezVous findRendezVous(@PathVariable(value = "id") Long id){
        return rendezVousService.findById(id);
    }



    // Get All Rendez-Vous d'un patient
    @GetMapping("/patients/{id}/rendezvous")
    public List<RendezVous> findAllRendezVousByPatient(@PathVariable(value = "id") Long id){
        return rendezVousService.findAllByPatient(id);
    }

    // Get All Rendez-Vous d'un patient
    @GetMapping("/comptePatients/{id}/rendezvous")
    public List<RendezVous> findAllRendezVousByComptePatient(@PathVariable(value = "id") Long id){
        return rendezVousService.findAllByComptePatient(id);
    }

    //Creer Rendez-Vous
    @PostMapping("/rendezvous")
    public RendezVous creerRendezVous(@Valid @RequestBody RendezVous rendezVous ) {
        return rendezVousService.creer(rendezVous);
  }

    @PutMapping("/rendezvous/{id}")
    public RendezVous updateRendezVous(@PathVariable (value = "id") Long id,
                                 @Valid @RequestBody RendezVous rendezVous){
        return rendezVousService.update(id, rendezVous);
    }

    @DeleteMapping("/rendezvous/{id}")
    public ResponseEntity<?> deleteRendezVous(@PathVariable (value = "id") Long id) {
        return rendezVousService.deleteRd(id);
    }
}
