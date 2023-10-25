package br.org.aly.util;

import br.org.aly.model.User;

public class UserCreator {
    public static User createUserTest() {
        return User.builder()
                .nome("Rodolfo")
                .profissao("QA")
                .idade(20).build();
    }

    public static User createUservalid() {
        return User.builder()
                .id_user(1L)
                .nome("Rodolfo")
                .profissao("QA")
                .idade(20).build();
    }

    public static User createUserUpdateTest() {
        return User.builder()
                .id_user(1L)
                .nome("Rodolfo")
                .profissao("Dev")
                .idade(20).build();
    }
}
