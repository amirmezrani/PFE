package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Specialite;
import tn.isimg.pfe.repository.SpecialiteRepository;
import java.util.List;

@Service
public class SpecialiteService {

    @Autowired
    SpecialiteRepository specialiteRepository;

    // fiind all Specialité
    public List<Specialite> findAll() {
        return specialiteRepository.findAll();
    }

    // fiind  Specialité By Id
    public Specialite findSpecialiteById(Long id){
        return specialiteRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("id Specialité " + id + " not found"));

    }

    // Creation specialité
    public Specialite creerSpecialite(Specialite specialite){
        specialiteRepository.findByLibelle(specialite.getLibelle()).ifPresent(s -> {
            throw new RuntimeException("specialite id : " +specialite.getId()+" est deja existe");
        });
        return specialiteRepository.save(specialite);
    }

    // Update Specialité
    public Specialite updateSpecialiteById(Long id, Specialite specialiteRequest){
        Specialite specialite=findSpecialiteById(id);
        specialite.setLibelle(specialiteRequest.getLibelle());
        return specialiteRepository.save(specialite);
    }

    // Delete Specialité
    public ResponseEntity<?> deleteSpecialiteById( Long id){
        Specialite specialite=findSpecialiteById(id);
        specialiteRepository.delete(specialite);
        return ResponseEntity.ok().build();
    }
}
