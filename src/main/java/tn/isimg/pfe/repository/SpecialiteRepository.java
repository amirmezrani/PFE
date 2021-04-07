package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.Specialite;

import java.util.Optional;

public interface SpecialiteRepository extends JpaRepository<Specialite,Long> {
    Optional<Specialite> findByLibelle(String libelle);
}
