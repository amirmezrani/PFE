package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ExistException;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Administrateur;
import tn.isimg.pfe.repository.AdministrateurRepository;

import java.util.List;

@Service
public class AdministrateurService {

    @Autowired
    AdministrateurRepository administrateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    // Get All Administrateur
    public List<Administrateur> findAllAdmin(){
        return administrateurRepository.findAll();
    }

    // Get All Administrateur
    public Administrateur findAdministrateurById(Long id){
        return administrateurRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("id Administrateur " + id + " not found"));
    }

    // find Admin by email
    public Administrateur getAdminByEmail(String email){
        Administrateur administrateur=administrateurRepository.findByEmail(email).
                orElseThrow(()->new ExistException("Email Admin "+ email+ " not found"));
        return administrateur;
    }

    // creer Utilisateur
    public Administrateur creer(Administrateur administrateur){
        administrateur.setMotDePasse(passwordEncoder.encode(administrateur.getMotDePasse()));
        return administrateurRepository.save(administrateur);
    }
    // Update Utilisateur
    public Administrateur updateAdmin(Long id, Administrateur administrateurRequest){
        Administrateur administrateur= findAdministrateurById(id);
        administrateur.setEmail(administrateurRequest.getEmail());
        administrateur.setMotDePasse(administrateurRequest.getMotDePasse());

        Administrateur updateAdministrateur=administrateurRepository.save(administrateur);
        return updateAdministrateur;
    }

    // Delete Utilisateur
    public ResponseEntity<?> deleteAdmin(Long id){
        Administrateur administrateur = findAdministrateurById(id);
        administrateurRepository.delete(administrateur);
        return ResponseEntity.ok().build();
    }
}
