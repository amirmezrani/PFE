package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.model.Medecin;
import tn.isimg.pfe.service.MedecinService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MedecinController {


    @Autowired
    MedecinService medecinService;

    /*// fiind all comptePatient
    @GetMapping("/medecins")
    public List<Medecin> findAllMedecin()
    {
        return medecinRepository.findByIdAndDate();
    }*/

    // fiind  Medecin By Id
    @GetMapping("/medecins/{id}")
    public Medecin findMedecinById(@PathVariable(value = "id") Long idMedecin)
    {
        return medecinService.getMedecinById(idMedecin);
    }

    // fiind  Medecin By Specialité
    @GetMapping("/specialites/{id}/medecins")
    public List<Medecin> findMedecinBySpecialite(@PathVariable(value = "id") Long id)
    {
        return medecinService.getMedecinBySpecialite(id);
    }

    // fiind  Medecin By Ville
    @GetMapping("/villes/{id}/medecins")
    public List<Medecin> findMedecinByVille(@PathVariable(value = "id") Long id)
    {
        return medecinService.getMedecinByVille(id);
    }

    // fiind  Medecin By Specialité and Ville
    @GetMapping("/specialites/{id}/villes/{id1}/medecins")
    public List<Medecin> findMedecinBySpecialite(@PathVariable(value = "id") Long idSpecialite,
                                                 @PathVariable(value = "id1") Long idVille)
    {
        return medecinService.getMedecinBySpecialitéAndVille(idSpecialite,idVille);
    }

    // fiind all medecin non accepter
    @GetMapping("/medecinsNonAccepter")
    public List<Medecin> findAllMedecinNonAccepter(){
        return medecinService.getAllMedecinNonAccepter();
    }



    // creer  Medecin
    @PostMapping("/medecins")
    public Medecin creerMedecin(@Valid @RequestBody Medecin medecin){
       return medecinService.creer(medecin);
    }

    // Update Medecin
    @PutMapping("/medecins/{id}")
    public Medecin updateMedecin(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Medecin medecin){
        return medecinService.update(id, medecin);
    }

    @PutMapping("/medecins/{id}/motDePasse")
    public Medecin modifierMotDePasseMedecin(@PathVariable(value = "id") Long id,
                                             @Valid @RequestBody MotDePasseInfo motDePasseInfo){
            return medecinService.modifierMotDePasse(id, motDePasseInfo);
    }

    //Accepter un medecin
    @PutMapping("/medecinsAccepter/{id}")
    public Medecin accepterMedecinById(@PathVariable(value = "id") Long id){
        return medecinService.accepterMedecin(id);
    }

    // Delete Medecin
    @DeleteMapping("/medecins/{id}")
    public ResponseEntity<?> deleteMedecin(@PathVariable(value = "id") Long id){
        return medecinService.delete(id);
    }
}
