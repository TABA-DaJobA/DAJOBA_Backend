package taba.dajoba.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import taba.dajoba.domain.QSelfIntroduction;
import taba.dajoba.domain.QUser;
import taba.dajoba.domain.SelfIntroduction;
import taba.dajoba.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SelfIntroRepository {
    private final EntityManager em;

    //==자소서 저장==//
    public void save(SelfIntroduction selfIntroduction) {
        em.persist(selfIntroduction);
    }

    //==자소서 하나 삭제==//
    public void delete(SelfIntroduction selfIntroduction) {
        em.remove(selfIntroduction);
    }

    //==자소서 하나 검색==//
    public SelfIntroduction findOne(Long id) {
        return em.find(SelfIntroduction.class, id);
    }

    //==특정 user의 자소서 전부 조회==//
    public List<SelfIntroduction> showAll(String userId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QSelfIntroduction selfIntro = QSelfIntroduction.selfIntroduction;
        return query
                .select(selfIntro)
                .from(selfIntro)
                .where(selfIntro.user.userId.eq(userId))
                .fetch();
    }

    //==자소서 이름으로 검색==//
    public List<SelfIntroduction> findByName(String userId, String introName) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QUser user = QUser.user;
        QSelfIntroduction selfIntro = QSelfIntroduction.selfIntroduction;
        return query
                .selectFrom(selfIntro)
                .join(user)
                .on(selfIntro.user.eq(user))
                .where(user.userId.eq(userId), selfIntro.introName.eq(introName))
                .fetch();
    }

}
