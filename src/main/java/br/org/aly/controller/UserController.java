package br.org.aly.controller;

import br.org.aly.DTO.UserDTO;
import br.org.aly.exceptions.BadRequestException;
import br.org.aly.model.User;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    @Operation(summary = "Lista de todos os usuários cadastrados no banco de dados! 🎲", tags = {"User GET"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Foi retornado com sucesso! 🟢"),
            @ApiResponse(responseCode = "400", description = "Não foi retornado como deveria! 🔴")})
    public ResponseEntity<Page<User>> list(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(userService.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/auth/{id}")
    public ResponseEntity<User> findByIdAuth(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(value = "/categoria")
    public ResponseEntity<List<User>> findByProfissao(@ParameterObject @RequestParam String profissao) {
        return ResponseEntity.ok(userService.findByProfissao(profissao));
    }

    @PostMapping("/create")
    public ResponseEntity<User> save(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/up")
    public ResponseEntity<Void> att(@Valid @RequestBody UserDTO userDTO) {
        userService.attUser(userDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
