package br.org.aly.repository;

import br.org.aly.model.User;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@Log4j2
@DataJpaTest
@DisplayName("Testes para o userRepository 🧪")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Teste de criação de usuário! 🧪")
    void save_PersistUser(){
        User user = createUserTest();
        User userSaved = this.userRepository.save(user);

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId_user()).isNotNull();
        Assertions.assertThat(userSaved.getNome()).isEqualTo(user.getNome());
        Assertions.assertThat(userSaved.getProfissao()).isEqualTo(user.getProfissao());
        Assertions.assertThat(userSaved.getIdade()).isNotNegative();
    }

    @Test
    @DisplayName("Teste de update do usuário! 🧪")
    void update_PersistUser(){
        User user = createUserTest();
        User userSaved = this.userRepository.save(user);

        userSaved.setNome("Rodolfo");
        userSaved.setProfissao("Dev Jr.");
        userSaved.setIdade(20);

        User userUpdated = this.userRepository.save(userSaved);

        Assertions.assertThat(userUpdated).isNotNull();
        Assertions.assertThat(userUpdated.getId_user()).isNotNull();
        Assertions.assertThat(userUpdated.getNome()).isEqualTo(user.getNome());
        Assertions.assertThat(userUpdated.getProfissao()).isEqualTo(user.getProfissao());
        Assertions.assertThat(userUpdated.getIdade()).isNotNegative();
    }

    @Test
    @DisplayName("Teste para deletar o usuário! 🧪")
    void delete_PersistUser(){
        User user = createUserTest();
        User userSaved = this.userRepository.save(user);

        this.userRepository.delete(userSaved);

        Optional<User> userOptional = this.userRepository.findById(userSaved.getId_user());

        Assertions.assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by profissao (usuário)! 🧪")
    void findByProfissao_PersistUser(){
        User user = createUserTest();
        User userSaved = this.userRepository.save(user);

        String profissao = userSaved.getProfissao();

        List<User> users = this.userRepository.findByProfissao(profissao);

        Assertions.assertThat(users).isNotEmpty();
        Assertions.assertThat(users).contains(userSaved);
    }

    @Test
    @DisplayName("Teste de criação de usuário! 🧪")
    void saveThrows_PersistUser(){
        User user = new User();

        Assertions.assertThatThrownBy(() -> this.userRepository.save(user))
                .isInstanceOf(ConstraintViolationException.class)
                .withFailMessage("🚨");

    }

    private User createUserTest(){
        return User.builder()
                .nome("Rodolfo")
                .profissao("QA")
                .idade(20).build();
    }
}