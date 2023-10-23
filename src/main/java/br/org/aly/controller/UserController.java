package br.org.aly.controller;

import br.org.aly.DTO.UserDTO;
import br.org.aly.exceptions.BadRequestException;
import br.org.aly.model.User;
import br.org.aly.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/")
    public ResponseEntity<Page<User>> list(Pageable pageable) {
        return ResponseEntity.ok(userService.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(value = "/categoria")
    public ResponseEntity<List<User>> findByProfissao(@RequestParam String profissao) {
        List<User> userServiceByProfissao = userService.findByProfissao(profissao);

        if (userServiceByProfissao.isEmpty()) {
            throw new BadRequestException("Essa profissão não existe! 🚨");
        }

        return ResponseEntity.ok(userServiceByProfissao);
    }

    @PostMapping("/create")
    public ResponseEntity<User> save(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> att(@RequestBody UserDTO userDTO) {
        userService.attUser(userDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
