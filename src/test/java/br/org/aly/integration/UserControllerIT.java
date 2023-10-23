package br.org.aly.integration;

import br.org.aly.DTO.UserDTO;
import br.org.aly.model.User;
import br.org.aly.repository.UserRepository;
import br.org.aly.util.UserCreator;
import br.org.aly.util.UserDTOCreator;
import br.org.aly.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Teste para listar usuário! 🧪")
    void lisTest() {
        User userSaved = userRepository.save(UserCreator.createUserTest());

        String expectedName = UserCreator.createUservalid().getNome();
        String expectedProfissao = UserCreator.createUservalid().getProfissao();
        Integer expectedIdade = UserCreator.createUservalid().getIdade();

        PageableResponse<User> usersPage = testRestTemplate.exchange("/users/", HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageableResponse<User>>() {
                }).getBody();

        Assertions.assertThat(usersPage).isNotNull();
        Assertions.assertThat(usersPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(usersPage.toList().get(0).getNome()).isEqualTo(expectedName);
        Assertions.assertThat(usersPage.toList().get(0).getProfissao()).isEqualTo(expectedProfissao);
        Assertions.assertThat(usersPage.toList().get(0).getIdade()).isNotNegative().isEqualTo(expectedIdade);

    }

    @Test
    @DisplayName("Teste para buscar o usuário pelo ID! 🧪")
    void findByIdTest() {
        User userSaved = userRepository.save(UserCreator.createUserTest());

        Long expectedId = userSaved.getId_user();

        User user = testRestTemplate.getForObject("/users/{id}", User.class, expectedId);

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId_user()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName("Teste para buscar o usuário pela profissão! 🧪")
    void findByProfissaoTest() {
        User userSaved = userRepository.save(UserCreator.createUserTest());

        String expectedProfissao = UserCreator.createUservalid().getProfissao();

        String url = String.format("/users/categoria?profissao=%s", expectedProfissao);

        List<User> users = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                }
        ).getBody();
        
        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getProfissao()).isEqualTo(expectedProfissao);

    }

    @Test
    @DisplayName("Teste para salvar o usuário! 🧪")
    void saveTest() {
        UserDTO userDTO = UserDTOCreator.userDtoCreateUservalid();

        ResponseEntity<User> userResponseEntity = testRestTemplate.postForEntity("/users/create", userDTO, User.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity).isNotNull()
                .isEqualTo(UserCreator.createUservalid());
    }

    @Test
    @DisplayName("Teste para atualizar o usuário! 🧪")
    void attTest() {
        User userSaved = userRepository.save(UserCreator.createUserTest());

        userSaved.setId_user(1L);
        userSaved.setNome("Garfield");
        userSaved.setProfissao("Dev Jr.");
        userSaved.setIdade(20);

        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users",
                    HttpMethod.PUT, new HttpEntity<>(userSaved), Void.class
                );

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Teste para deletar o usuário! 🧪")
    void deleteTest() {
        User userSaved = userRepository.save(UserCreator.createUserTest());


        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users",
                HttpMethod.DELETE, null, Void.class, userSaved.getId_user()
        );

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
