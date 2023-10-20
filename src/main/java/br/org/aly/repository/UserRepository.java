package br.org.aly.repository;

import br.org.aly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByProfissao(String profissao);
}
