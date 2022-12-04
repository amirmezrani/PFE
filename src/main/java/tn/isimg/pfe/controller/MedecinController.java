package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.model.Medecin;
import tn.isimg.pfe.service.MedecinService;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MedecinController {


    @Autowired
    MedecinService medecinService;


    // fiind  All Medecin
    @GetMapping("/administrateurs/medecins")
    public List<Medecin> findMedecinAll()
    {
        return medecinService.getAllMedecin();
    }

    // fiind  All Medecin refuser
    @GetMapping("/administrateurs/medecins/blackList")
    public List<Medecin> blackListMedecin()
    {
        return medecinService.getBlackListMedecin();
    }


    // fiind  Medecin By Id
    @GetMapping("/medecins/{id}")
    public Medecin findMedecinById(@PathVariable(value = "id") Long idMedecin)
    {
        return medecinService.getMedecinById(idMedecin);
    }

    // fiind  Medecin By email
    @GetMapping("/medecins/email")
    public Medecin findMedecinByEmail(@RequestParam(value = "email") String email)
    {
        return medecinService.getMedecinByEmail(email);
    }

    // fiind  Medecin By Specialité
    @GetMapping("/specialites/{idSpecialite}/medecins")
    public List<Medecin> findMedecinBySpecialite(@PathVariable(value = "idSpecialite") Long idSpecialite)
    {
        return medecinService.getMedecinBySpecialite(idSpecialite);
    }

    // fiind  Medecin By Ville
    @GetMapping("/villes/{idVille}/medecins")
    public List<Medecin> findMedecinByVille(@PathVariable(value = "idVille") Long idVille)
    {
        return medecinService.getMedecinByVille(idVille);
    }

    // fiind  Medecin By Specialité and Ville
    @GetMapping("/medecins")
    public List<Medecin> findMedecinBySpecialite(@RequestParam(value = "idSpecialite") Long idSpecialite,
                                                 @RequestParam(value = "idVille") Long idVille)
    {
        return medecinService.getMedecinBySpecialiteAndVille(idSpecialite,idVille);
    }

    // fiind all medecin non accepter
    @GetMapping("/administrateurs/medecinsNonAccepter")
    public List<Medecin> findAllMedecinNonAccepter(){
        return medecinService.getAllMedecinNonAccepter();
    }



    // creer  Medecin
    @PostMapping("/medecins")
    public Medecin creerMedecin(@Valid @RequestBody Medecin medecin){
       return medecinService.creerMedecin(medecin);
    }

    // creer  Medecin pour Admin
    @PostMapping("/administrateurs/medecins")
    public Medecin creerMedecinForAdmin(@Valid @RequestBody Medecin medecin){
        return medecinService.creerMedecin(medecin);
    }
    // Update Medecin
    @PutMapping("/medecins/{id}")
    public Medecin modifierMedecin(@PathVariable(value = "id") Long id,
                                   @Valid @RequestBody Medecin medecin){
        return medecinService.updateMedecin(id, medecin);
    }


    // Update Medecin pour Admin
    @PutMapping("/administrateurs/medecins/{id}")
    public Medecin updateMedecinForAdmin(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Medecin medecin){
        return medecinService.updateMedecin(id, medecin);
    }

    @PutMapping("/medecins/{id}/motDePasse")
    public Medecin modifierMotDePasseMedecin(@PathVariable(value = "id") Long id,
                                             @Valid @RequestBody MotDePasseInfo motDePasseInfo){
            return medecinService.modifierMotDePasseMedecin(id, motDePasseInfo);
    }

    //Accepter un medecin
    @PutMapping("/administrateurs/accepterMedecins/{id}")
    public Medecin accepterMedecinById(@PathVariable(value = "id") Long id){
        return medecinService.accepterMedecin(id);
    }

    //rejeter un medecin
    @PutMapping("/administrateurs/rejeterMedecins/{id}")
    public Medecin rejeterMedecinById(@PathVariable(value = "id") Long id){
        return medecinService.rejeterMedecin(id);
    }

    // Delete Medecin
    @DeleteMapping("/medecins/{id}")
    public ResponseEntity<?> deleteMedecin(@PathVariable(value = "id") Long id){
        return medecinService.deleteMed(id);
    }

    // Delete Medecin pour Admin
    @DeleteMapping("/administrateurs/medecins/{id}")
    public ResponseEntity<?> deleteMedecinForAdmin(@PathVariable(value = "id") Long id){
        return medecinService.deleteMed(id);
    }

}
