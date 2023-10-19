package br.org.aly.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Long id_user;
    private String nome;
    private String profissao;
    private Integer idade;
}
