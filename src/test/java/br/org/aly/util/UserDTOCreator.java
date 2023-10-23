package br.org.aly.util;

import br.org.aly.DTO.UserDTO;
import br.org.aly.model.User;

public class UserDTOCreator {
    public static UserDTO userDtoCreateUservalid() {
        return UserDTO.builder()
                .id_user(UserCreator.createUserTest().getId_user())
                .nome(UserCreator.createUserTest().getNome())
                .profissao(UserCreator.createUserTest().getProfissao())
                .idade(UserCreator.createUserTest().getIdade())
                .build();
    }

    public static UserDTO userDtoUpdateUservalid() {
        return UserDTO.builder()
                .id_user(UserCreator.createUserUpdateTest().getId_user())
                .nome(UserCreator.createUserUpdateTest().getNome())
                .profissao(UserCreator.createUserUpdateTest().getProfissao())
                .idade(UserCreator.createUserUpdateTest().getIdade())
                .build();
    }
}
