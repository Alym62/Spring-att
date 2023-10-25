package br.org.aly.repository;

import br.org.aly.model.AlyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevUserRepository extends JpaRepository<AlyUser, Long> {
    AlyUser findByUsername(String username);
}
