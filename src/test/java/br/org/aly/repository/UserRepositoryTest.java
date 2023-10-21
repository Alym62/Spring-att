package br.org.aly.repository;

import br.org.aly.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    private User createUserTest(){
        return User.builder()
                .nome("Rodolfo")
                .profissao("QA")
                .idade(20).build();
    }
}