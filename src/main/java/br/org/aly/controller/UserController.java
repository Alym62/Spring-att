package br.org.aly.controller;

import br.org.aly.DTO.UserDTO;
import br.org.aly.model.User;
import br.org.aly.repository.UserCustomRepository;
import br.org.aly.repository.UserRepository;
import br.org.aly.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    private final UserCustomRepository userCustomRepository;

    @GetMapping("/find")
    @Operation(summary = "Lista de todos os usuários, baseado na idade e profissão", tags = {"User GET"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Foi retornado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Não existe um usuário com esses parâmetros. Olhe a documentação! 🔴")})
    public ResponseEntity<List<User>> controllerFindByIdadeAndProfissao(Integer idade, String profissao) {
        return ResponseEntity.ok(userRepository.findByIdadeAndProfissao(idade, profissao));
    }

    @GetMapping("/order")
    @Operation(summary = "Lista os usuários com o mesmo nome.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Foi retornado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Não existe um usuário com esse nome. Olhe a documentação! 🔴")})
    public List<User> findTestes(@RequestParam("nome") String nome) {
        return userCustomRepository.consultaCriteria(nome);
    }

    @GetMapping("/")
    @Operation(summary = "Lista de todos os usuários cadastrados no banco de dados! 🎲", tags = {"User GET"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Foi retornado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Não foi retornado como deveria! 🔴")})
    public ResponseEntity<Page<User>> list(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(userService.listAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca o usuário pelo ID.", tags = {"User GET"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Foi retornado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Esse usuário não existe! 🔴")})
    public ResponseEntity<User> findById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    @GetMapping(value = "/categoria")
    @Operation(summary = "Buscar pela profissão do usuário.", tags = {"User GET"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Foi retornado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Não existe essa profissão! 🔴")})
    public ResponseEntity<List<User>> findByProfissao(@ParameterObject @RequestParam String profissao) {
        return ResponseEntity.ok(userService.findByProfissao(profissao));
    }

    @PostMapping("/create")
    @Operation(summary = "Criar o usuário.", tags = {"User POST"})
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Foi criado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Não existe essa profissão! 🔴")})
    public ResponseEntity<User> save(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/up")
    @Operation(summary = "Atualizar o usuário.", tags = {"User PUT"})
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Foi atualizado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Não é possível atualizar o usuário. Olhe a documentação! 🔴")})
    public ResponseEntity<Void> att(@Valid @RequestBody UserDTO userDTO) {
        userService.attUser(userDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Deleta o usuário.", tags = {"User DELETE"})
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso! 🟢'"),
            @ApiResponse(responseCode = "400", description = "Não é possível deletar o usuário. Olhe a documentação! 🔴")})
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
