package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.Administrateur;
import tn.isimg.pfe.model.ComptePatient;

import java.util.Optional;

public interface AdministrateurRepository extends JpaRepository<Administrateur,Long> {

    Optional<Administrateur> findByEmail(String email);
}
