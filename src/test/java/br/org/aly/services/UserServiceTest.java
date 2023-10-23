package br.org.aly.services;

import br.org.aly.model.User;
import br.org.aly.repository.UserRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<User> userPage = new PageImpl<>(List.of(UserCreator.createUservalid()));

        BDDMockito.when(userRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                        .thenReturn(userPage);

        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UserCreator.createUservalid()));

        BDDMockito.when(userRepositoryMock.findByProfissao(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createUservalid()));

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createUservalid());


        BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(User.class));

    }

    @Test
    @DisplayName("Teste do service para listar usuário! 🧪")
    void lisTest() {
        String expectedName = UserCreator.createUservalid().getNome();
        String expectedProfissao = UserCreator.createUservalid().getProfissao();
        Integer expectedIdade = UserCreator.createUservalid().getIdade();

        Page<User> userPage = userService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(userPage).isNotNull();
        Assertions.assertThat(userPage.toList()).isNotEmpty();
        Assertions.assertThat(userPage.toList().get(0).getNome()).isEqualTo(expectedName);
        Assertions.assertThat(userPage.toList().get(0).getProfissao()).isEqualTo(expectedProfissao);
        Assertions.assertThat(userPage.toList().get(0).getIdade()).isNotNegative().isEqualTo(expectedIdade);
    }

    @Test
    @DisplayName("Teste do service para buscar o usuário pelo ID! 🧪")
    void findByIdTest() {
        Long expectedId = UserCreator.createUservalid().getId_user();

        User user = userService.findById(1);

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId_user()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName("Teste do service para buscar o usuário pela profissão! 🧪")
    void findByProfissaoTest() {
        String expectedProfissao = UserCreator.createUservalid().getProfissao();

        List<User> users = userService.findByProfissao("Dev");

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getProfissao()).isEqualTo(expectedProfissao);

    }

    @Test
    @DisplayName("Teste do service para salvar o usuário! 🧪")
    void saveTest() {

        User user = userService.saveUser(UserDTOCreator.userDtoCreateUservalid());

        Assertions.assertThat(user).isNotNull()
                .isEqualTo(UserCreator.createUservalid());
    }

    @Test
    @DisplayName("Teste do service para atualizar o usuário! 🧪")
    void attTest() {
        Assertions.assertThatCode(() -> userService.attUser(UserDTOCreator.userDtoUpdateUservalid()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Teste do service para deletar o usuário! 🧪")
    void deleteTest() {
        Assertions.assertThatCode(() -> userService.deleteUser(1))
                .doesNotThrowAnyException();
    }

}