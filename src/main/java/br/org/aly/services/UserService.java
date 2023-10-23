package br.org.aly.services;

import br.org.aly.DTO.UserDTO;
import br.org.aly.exceptions.BadRequestException;
import br.org.aly.exceptions.BadRequestExceptionDetails;
import br.org.aly.model.User;
import br.org.aly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);

    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuário não existe no bando de dados! 🚨"));
    }

    public List<User> findByProfissao(String profissao) {
        return userRepository.findByProfissao(profissao);
    }

    @Transactional
    public User saveUser(UserDTO userDTO) {
        User user = User.builder()
                .nome(userDTO.getNome()).profissao(userDTO.getProfissao()).idade(userDTO.getIdade())
                .build();
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.delete(findById(id));
    }

    public void attUser(UserDTO userDTO) {
        findById(userDTO.getId_user());

        User user = User.builder()
                .id_user(userDTO.getId_user()).nome(userDTO.getNome()).profissao(userDTO.getProfissao())
                .idade(userDTO.getIdade()).build();

        userRepository.save(user);
    }
}
