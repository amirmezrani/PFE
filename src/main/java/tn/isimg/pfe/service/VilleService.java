package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ResourceNotFoundException;
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

    // fiind  Ville By Id
    public Ville getVilleById(Long id){
        return villeRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("id Ville " + id + " not found"));

    }

    // Creation ville
    public Ville creerVille(Ville ville){
        villeRepository.findByVille(ville.getVille()).ifPresent(s -> {
            throw new RuntimeException("ville  : " +ville.getVille()+" est deja existe");
        });
        return villeRepository.save(ville);
    }

    // Update Ville
    public Ville updateVilleById(Long id, Ville villeRequest){
        Ville ville= getVilleById(id);
        ville.setVille(villeRequest.getVille());
        return villeRepository.save(ville);
    }

}
