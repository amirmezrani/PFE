package tn.isimg.pfe.security;

import tn.isimg.pfe.enumeration.RoleEnum;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Administrateur;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.repository.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    AdministrateurRepository administrateurRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // Let people login with either  email
        Administrateur user = administrateurRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with  email : " + email)
                );

        System.out.println(user.getMotDePasse());

        return UserPrincipal.create(user.getId(), user.getEmail(),user.getMotDePasse(), RoleEnum.ROLE_ADMIN);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        Administrateur user = administrateurRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user.getId(), user.getEmail(),user.getMotDePasse(), RoleEnum.ROLE_ADMIN);
    }
}