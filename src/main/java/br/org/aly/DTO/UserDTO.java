package br.org.aly.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    private Long id_user;

    @NotNull(message = "Não é possível aceitar um null! 🚨")
    @NotEmpty(message = "Por favor preencha o nome do usuário! 🚨")
    private String nome;

    @NotNull(message = "Não é possível aceitar um null! 🚨")
    @NotEmpty(message = "Por favor preencha a profissão do usuário! 🚨")
    private String profissao;

    @Min(value = 1, message = "Coloque uma idade válida por favor! 🚨")
    private Integer idade;

}
