package br.org.aly.controller;

import br.org.aly.DTO.UserDTO;
import br.org.aly.model.User;
import br.org.aly.services.UserService;
import br.org.aly.util.UserCreator;
import br.org.aly.util.UserDTOCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMook;

    @BeforeEach
    void setUp() {
        PageImpl<User> userPage = new PageImpl<>(List.of(UserCreator.createUservalid()));

        BDDMockito.when(userServiceMook.listAll(ArgumentMatchers.any()))
                .thenReturn(userPage);

        BDDMockito.when(userServiceMook.findById(ArgumentMatchers.anyLong()))
                .thenReturn(UserCreator.createUservalid());

        BDDMockito.when(userServiceMook.findByProfissao(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createUservalid()));

        BDDMockito.when(userServiceMook.saveUser(ArgumentMatchers.any(UserDTO.class)))
                .thenReturn(UserCreator.createUservalid());

        BDDMockito.doNothing().when(userServiceMook).attUser(ArgumentMatchers.any(UserDTO.class));

        BDDMockito.doNothing().when(userServiceMook).deleteUser(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("Teste para listar usuário! 🧪")
    void lisTest() {
        String expectedName = UserCreator.createUservalid().getNome();
        String expectedProfissao = UserCreator.createUservalid().getProfissao();
        Integer expectedIdade = UserCreator.createUservalid().getIdade();

        Page<User> userPage = userController.list(null).getBody();

        Assertions.assertThat(userPage).isNotNull();
        Assertions.assertThat(userPage.toList()).isNotEmpty();
        Assertions.assertThat(userPage.toList().get(0).getNome()).isEqualTo(expectedName);
        Assertions.assertThat(userPage.toList().get(0).getProfissao()).isEqualTo(expectedProfissao);
        Assertions.assertThat(userPage.toList().get(0).getIdade()).isNotNegative().isEqualTo(expectedIdade);
    }

    @Test
    @DisplayName("Teste para buscar o usuário pelo ID! 🧪")
    void findByIdTest() {
        Long expectedId = UserCreator.createUservalid().getId_user();

        User user = userController.findById(1).getBody();

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId_user()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName("Teste para buscar o usuário pela profissão! 🧪")
    void findByProfissaoTest() {
        String expectedProfissao = UserCreator.createUservalid().getProfissao();

        List<User> users = userController.findByProfissao("Dev").getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getProfissao()).isEqualTo(expectedProfissao);

    }

    @Test
    @DisplayName("Teste para salvar o usuário! 🧪")
    void saveTest() {

        User user = userController.save(UserDTOCreator.userDtoCreateUservalid()).getBody();

        Assertions.assertThat(user).isNotNull()
                .isEqualTo(UserCreator.createUservalid());
    }

    @Test
    @DisplayName("Teste para atualizar o usuário! 🧪")
    void attTest() {
        Assertions.assertThatCode(() -> userController.att(UserDTOCreator.userDtoUpdateUservalid()))
                        .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Teste para deletar o usuário! 🧪")
    void deleteTest() {
        Assertions.assertThatCode(() -> userController.delete(1))
                .doesNotThrowAnyException();
    }

}