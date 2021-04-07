package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.exception.MotDePasseException;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Disponibilite;
import tn.isimg.pfe.model.Medecin;
import tn.isimg.pfe.repository.MedecinRepository;
import tn.isimg.pfe.repository.SpecialiteRepository;
import tn.isimg.pfe.repository.VilleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedecinService {

    @Autowired
    MedecinRepository medecinRepository;

    @Autowired
    SpecialiteRepository specialiteRepository;

    @Autowired
    VilleRepository villeRepository;

    // fiind  Medecin By Id
    public Medecin getMedecinById(Long idMedecin)
    {
        return medecinRepository.findById(idMedecin).
                orElseThrow(() -> new ResourceNotFoundException("id Medecin " + idMedecin + " not found"));
    }

    // fiind  Medecin By Specialité
    public List<Medecin> getMedecinBySpecialite(Long id)
    {
        return medecinRepository.findBySpecialiteId(id);
    }

    // fiind  Medecin By Ville
    public List<Medecin> getMedecinByVille(Long id)
    {
        return medecinRepository.findByVilleId(id);
    }

    // fiind  Medecin By Specialité and Ville
    public List<Medecin> getMedecinBySpecialitéAndVille(Long idSpecialite,Long idVille)
    {
        return medecinRepository.findBySpecialiteIdAndVilleId(idSpecialite,idVille);
    }

    // fiind all medecin non accepter
    public List<Medecin> getAllMedecinNonAccepter(){
        List<Medecin> medecins=medecinRepository.findAll();
        List<Medecin> medecins1=new ArrayList<>();

        for (Medecin medecin:medecins) {

            if (medecin.getValider().equals(false)){
                medecins1.add(medecin);
            }

        }
        return medecins1;
    }

    // creer  Medecin
    public Medecin creer( Medecin medecin){
        medecinRepository.findByEmail(medecin.getEmail()).ifPresent(md -> {
            throw new RuntimeException("medecin id : " +medecin.getId()+" est deja existe");
        });
    return specialiteRepository.findById(medecin.getSpecialite().getId())
                .map(specialite -> {
                    medecin.setSpecialite(specialite);

                        villeRepository.findById(medecin.getVille().getId()).map(ville -> {
                            medecin.setVille(ville);
                            return medecinRepository.save(medecin);
                        }).orElseThrow(() -> new ResourceNotFoundException("specialité id : "
                                +villeRepository.findById(medecin.getVille().getId())+"n'existe pas"));

                    return medecinRepository.save(medecin);
                }).orElseThrow(() -> new ResourceNotFoundException("specialité id : "
                    +specialiteRepository.findById(medecin.getSpecialite().getId())+"n'existe pas"));
    }

    // Update Medecin
    public Medecin update( Long id,Medecin medecinRequest){
        medecinRepository.findByEmail(medecinRequest.getEmail()).ifPresent(md -> {
            throw new RuntimeException("medecin id : " +medecinRequest.getId()+" est deja existe");
        });
        Medecin medecin=medecinRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id Medecin " + id + " not found"));
        medecin.setEmail(medecinRequest.getEmail());
        medecin.setPrenom(medecinRequest.getPrenom());
        medecin.setNom(medecinRequest.getNom());
        medecin.setVille(medecinRequest.getVille());
        medecin.setAdresse(medecinRequest.getAdresse());
        medecin.setConact(medecinRequest.getConact());
        medecin.setContactUrgence(medecinRequest.getContactUrgence());
        medecin.setPresentation(medecinRequest.getPresentation());
        medecin.setSpecialite(medecinRequest.getSpecialite());

        Medecin updateMedecin=medecinRepository.save(medecin);
        return updateMedecin;
    }


    public Medecin modifierMotDePasse(Long id, MotDePasseInfo motDePasseInfo){
        Medecin  medecin=getMedecinById(id);
        if (motDePasseInfo.getAncienMotDePasse().equals(medecin.getMotDePasse())){
            medecin.setMotDePasse(motDePasseInfo.getNouveauMotDePasse());
            Medecin updateMedecin=medecinRepository.save(medecin);
            return updateMedecin;
        }
        else
        {
            throw new MotDePasseException("mot de passe invalid");
        }
    }

    //Accepter un medecin
    public Medecin accepterMedecin( Long id){
        Medecin medecin=getMedecinById(id);
        medecin.setValider(true);
        return medecinRepository.save(medecin);
    }

    // Delete Medecin
    public ResponseEntity<?> delete(Long id){
        Medecin medecin=medecinRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id Medecin " + id + " not found"));
        medecinRepository.delete(medecin);
        return ResponseEntity.ok().build();
    }
}
