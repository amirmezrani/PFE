package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.exception.ExistException;
import tn.isimg.pfe.exception.MotDePasseException;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Medecin;
import tn.isimg.pfe.repository.MedecinRepository;
import tn.isimg.pfe.repository.SpecialiteRepository;
import tn.isimg.pfe.repository.VilleRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSenderService mailSenderService;

    // fiind  All Medecin
    public List<Medecin> getAllMedecin() {
        List<Medecin> medecins=medecinRepository.findAll();
        List<Medecin> medecins1=new ArrayList<>();

        for (Medecin medecin:medecins) {

            if (medecin.getValider().equals(true)){
                medecins1.add(medecin);
            }

        }
        return medecins1;
    }

    // fiind  All Medecin refuser
    public List<Medecin> getBlackListMedecin() {
        List<Medecin> medecins=medecinRepository.findAll();
        List<Medecin> medecins1=new ArrayList<>();

        for (Medecin medecin:medecins) {

            if (medecin.getRejeter().equals(true)){
                medecins1.add(medecin);
            }

        }
        return medecins1;
    }


    // fiind  Medecin By Id
    public Medecin getMedecinById(Long idMedecin)
    {
        return medecinRepository.findById(idMedecin).
                orElseThrow(() -> new ResourceNotFoundException("id Medecin " + idMedecin + " not found"));
    }

    // find medecin by email
    public Medecin getMedecinByEmail(String email){
        Medecin medecin=medecinRepository.findByEmail(email).
                orElseThrow(()->new ResourceNotFoundException("Email Medecin "+ email+ " not found"));
        return medecin;
    }

    // fiind  Medecin By Specialité
    public List<Medecin> getMedecinBySpecialite(Long idSpecialite)
    {
        return medecinRepository.findBySpecialiteId(idSpecialite);
    }

    // fiind  Medecin By Ville
    public List<Medecin> getMedecinByVille(Long idVille)
    {
        return medecinRepository.findByVilleId(idVille);
    }

    // fiind  Medecin By Specialité and Ville
    public List<Medecin> getMedecinBySpecialiteAndVille(Long idSpecialite, Long idVille) {
        if (idSpecialite == null && idVille == null) {
            throw new RuntimeException("missing parameters");
        } else if (idSpecialite == null) {
            return medecinRepository.findByVilleId(idVille);
        } else if (idVille == null) {
            return medecinRepository.findBySpecialiteId(idSpecialite);
        } else {
            return medecinRepository.findBySpecialiteIdAndVilleId(idSpecialite, idVille);
        }
    }

    // fiind all medecin non accepter
    public List<Medecin> getAllMedecinNonAccepter(){
        List<Medecin> medecins=medecinRepository.findAll();
        List<Medecin> medecins1=new ArrayList<>();

        for (Medecin medecin:medecins) {

            if (medecin.getValider().equals(false)&&(medecin.getRejeter().equals(false))){
                medecins1.add(medecin);
            }

        }
        return medecins1;
    }

    // creer  Medecin
    public Medecin creerMedecin(Medecin medecin){
        medecinRepository.findByEmail(medecin.getEmail()).ifPresent(md -> {
            throw new ExistException("medecin id : " +medecin.getId()+" est deja existe");
        });
    return specialiteRepository.findById(medecin.getSpecialite().getId())
                .map(specialite -> {
                    medecin.setSpecialite(specialite);

                        villeRepository.findById(medecin.getVille().getId()).map(ville -> {
                            medecin.setVille(ville);
                            return medecinRepository.save(medecin);
                        }).orElseThrow(() -> new ResourceNotFoundException("specialité id : "
                                +villeRepository.findById(medecin.getVille().getId())+"n'existe pas"));
                    medecin.setMotDePasse(passwordEncoder.encode(medecin.getMotDePasse()));
                    return medecinRepository.save(medecin);

                }).orElseThrow(() -> new ResourceNotFoundException("specialité id : "
                    +specialiteRepository.findById(medecin.getSpecialite().getId())+"n'existe pas"));
    }

    // Update Medecin
    public Medecin updateMedecin(Long id, Medecin medecinRequest){
        Medecin medecin=medecinRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id Medecin " + id + " not found"));


//        medecinRepository.findByEmail(medecinRequest.getEmail()).ifPresent(md -> {
//            throw new ExistException("medecin email : " +medecinRequest.getEmail()+" est deja existe");
//        });

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


    public Medecin modifierMotDePasseMedecin(Long id, MotDePasseInfo motDePasseInfo){
        Medecin  medecin=getMedecinById(id);
        String mdpAncien=motDePasseInfo.getAncienMotDePasse();

        if (passwordEncoder.matches(mdpAncien,medecin.getMotDePasse())){
            medecin.setMotDePasse(passwordEncoder.encode(motDePasseInfo.getNouveauMotDePasse()));
            Medecin updateMedecin=medecinRepository.save(medecin);
            try {
                this.mailSenderService.send(medecin.getEmail(),
                        "Votre Mot De Passe est changé  ",
                        "merci de consulter votre compte");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return updateMedecin;
        } else {
            throw new MotDePasseException("mot de passe invalid");
        }
    }

    //Accepter un medecin
    public Medecin accepterMedecin( Long id){
        Medecin medecin=getMedecinById(id);
        medecin.setValider(true);

        try {
            this.mailSenderService.send(medecin.getEmail(),
                    "Demande Compte","Bonjour,<br>\n " +
                            "Votre demande d'inscription sur notre site a été acceptée.<br>\n " +
                            "Merci pour votre confiance.<br>\n" +
                            " Cordialement");
                   // +"<img width=100% src='../../../resources/Recherche.png'>");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return medecinRepository.save(medecin);
    }

    //rejeter un medecin
    public Medecin rejeterMedecin( Long id){
        Medecin medecin=getMedecinById(id);
        medecin.setRejeter(true);
        try {
            this.mailSenderService.send(medecin.getEmail(),
                    "Demande Compte ",
                    "Votre compte est refusé");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return medecinRepository.save(medecin);
    }

    // Delete Medecin
    public ResponseEntity<?> deleteMed(Long id){
        Medecin medecin=getMedecinById(id);
        medecinRepository.delete(medecin);
        return ResponseEntity.ok().build();
    }
}
