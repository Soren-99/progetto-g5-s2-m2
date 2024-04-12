package sorenrahimi.progettog5s2m2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sorenrahimi.progettog5s2m2.entities.Dipendente;

import java.util.Optional;

public interface DipendentiRepository extends JpaRepository<Dipendente, Integer> {

    Optional<Dipendente> findByEmail(String email);
}
