package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.model.Ville;
import tn.isimg.pfe.repository.VilleRepository;

import java.util.List;

@Service
public class VilleService {
    @Autowired
    VilleRepository villeRepository;

    public List<Ville> getAllVille(){
        return villeRepository.findAll();
    }
    // Creation ville
    public Ville creerS(Ville ville){
        /*villeRepository.findByLibelle(ville.getLibelle()).ifPresent(s -> {
            throw new RuntimeException("specialite id : " +ville.getId()+" est deja existe");
        });*/
        return villeRepository.save(ville);
    }
}
