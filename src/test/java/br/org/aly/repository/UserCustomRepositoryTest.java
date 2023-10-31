package br.org.aly.repository;

import br.org.aly.model.User;
import br.org.aly.util.UserCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@DataJpaTest
@DisplayName("Teste do criteria builder")
@Log4j2
@ComponentScan
class UserCustomRepositoryTest {

    @Autowired
    UserCustomRepository userCustomRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void userCustomRepositoryTest(){
        User user = this.userRepository.save(UserCreator.createUserTest());

        List<User> userList = this.userCustomRepository.consultaCriteria("Rodolfo");

        log.info(userList);

        Assertions.assertThat(userList).isNotNull().hasSize(1);
        Assertions.assertThat(userList.get(0).getNome()).isEqualTo(user.getNome());
    }
}