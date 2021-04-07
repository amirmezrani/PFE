package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Administrateur;
import tn.isimg.pfe.repository.AdministrateurRepository;

import java.util.List;

@Service
public class AdministrateurService {

    @Autowired
    AdministrateurRepository administrateurRepository;


    // Get All Administrateur
    public List<Administrateur> findAllAdmin(){
        return administrateurRepository.findAll();
    }

    // Get All Administrateur
    public Administrateur findadministrateur( Long id){
        return administrateurRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("id Administrateur " + id + " not found"));
    }

    // creer Utilisateur
    public Administrateur creer(Administrateur administrateur){
        return administrateurRepository.save(administrateur);
    }
    // Update Utilisateur
    public Administrateur updateAdmin(Long id, Administrateur administrateurRequest){
        Administrateur administrateur=administrateurRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id Administrateur " + id + " not found"));
        administrateur.setEmail(administrateurRequest.getEmail());
        administrateur.setMotDePasse(administrateurRequest.getMotDePasse());

        Administrateur updateAdministrateur=administrateurRepository.save(administrateur);
        return updateAdministrateur;
    }

    // Delete Utilisateur
    public ResponseEntity<?> deleteAdmin(Long id){
        Administrateur administrateur =administrateurRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id Administrateur " + id + " not found"));
        administrateurRepository.delete(administrateur);
        return ResponseEntity.ok().build();
    }
}
