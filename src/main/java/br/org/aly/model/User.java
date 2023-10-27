package br.org.aly.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuarios")
@Table(name = "usuarios")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Esse é o ID do usuário. Ele será preenchido automaticamente.")
    private Long id_user;

    @NotNull(message = "Não é possível aceitar um null! 🚨")
    @NotEmpty(message = "Por favor preencha o nome do usuário! 🚨")
    @Schema(description = "Esse é o nome do usuário.", example = "Rodolfo", required = true)
    private String nome;

    @NotNull(message = "Não é possível aceitar um null! 🚨")
    @NotEmpty(message = "Por favor preencha a profissão do usuário! 🚨")
    @Schema(description = "Essa é a profissão do usuário.", example = "Desenvolvedor pleno", required = true)
    private String profissao;

    @Min(value = 1, message = "Coloque uma idade válida por favor! 🚨")
    @Schema(description = "Essa é a idade do usuário.", example = "20", required = true)
    private Integer idade;
}
