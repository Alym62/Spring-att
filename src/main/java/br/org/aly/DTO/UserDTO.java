package br.org.aly.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id_user;

    @NotNull(message = "Não é possível aceitar um null! 🚨")
    @NotEmpty(message = "Por favor preencha o nome do usuário! 🚨")
    private String nome;

    @NotNull(message = "Não é possível aceitar um null! 🚨")
    @NotEmpty(message = "Por favor preencha a profissão do usuário! 🚨")
    @Schema(description = "Essa é a profissão do usuário", example = "Desenvolvedor pleno", required = true)
    private String profissao;

    @Min(value = 1, message = "Coloque uma idade válida por favor! 🚨")
    private Integer idade;

}
