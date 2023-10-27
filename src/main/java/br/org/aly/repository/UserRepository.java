package br.org.aly.repository;

import br.org.aly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByProfissao(String profissao);

    @Query(value = "SELECT u FROM usuarios u WHERE u.idade = :idade AND u.profissao = :profissao")
    List<User> findByIdadeAndProfissao(Integer idade, String profissao);

}
