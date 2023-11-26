package taba.dajoba.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import taba.dajoba.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id){ //test용
        return em.find(User.class, id);
    }

    public List<User> findByUserId(String userId){
        return em.createQuery("select u from User u where u.userId = :userId",
                User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
