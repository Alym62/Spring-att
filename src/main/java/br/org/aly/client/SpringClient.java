package br.org.aly.client;

import br.org.aly.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args){
        ResponseEntity<User> entity = new RestTemplate().getForEntity("http://localhost:8080/users/1", User.class);
        log.info(entity);

        User objectUser = new RestTemplate().getForObject("http://localhost:8080/users/1", User.class);
        log.info(objectUser);

        User devGarfield = User.builder().nome("Garfield").idade(18).profissao("Dev Jr.").build();
        ResponseEntity<User> garfieldSaved = new RestTemplate()
                .exchange("http://localhost:8080/users/create", HttpMethod.POST, new HttpEntity<>(devGarfield, createJsonHeader()), User.class);
        log.info("Usuário salvo{}", garfieldSaved);

        User userUpdate = garfieldSaved.getBody();
        userUpdate.setIdade(22);
        ResponseEntity<Void> devGarfiledUpdate = new RestTemplate()
                .exchange("http://localhost:8080/users", HttpMethod.PUT, new HttpEntity<>(devGarfield, createJsonHeader()), Void.class);
        log.info(devGarfiledUpdate);

        ResponseEntity<Void> userDelete = new RestTemplate()
                .exchange("http://localhost:8080/users/{id}", HttpMethod.DELETE, null, Void.class, userUpdate.getId_user());
        log.info(userDelete);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
