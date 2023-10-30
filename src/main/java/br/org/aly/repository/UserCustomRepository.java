package br.org.aly.repository;

import br.org.aly.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class UserCustomRepository {
    private final EntityManager entityManager;

    public UserCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> consultaCriteria(String nome){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("nome"), nome));

        List<User> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        log.info(resultList);

        return resultList;

    }
}
