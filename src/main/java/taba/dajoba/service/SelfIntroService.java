package taba.dajoba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.dajoba.domain.SelfIntroduction;
import taba.dajoba.domain.User;
import taba.dajoba.repository.SelfIntroRepository;
import taba.dajoba.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelfIntroService {

    private final SelfIntroRepository selfIntroRepository;
    private final UserRepository userRepository;

    /**
     * 자소서
     */
    @Transactional
    public SelfIntroduction selfIntro(String userId, String introName, String introContent, int field) {
        List<User> users = userRepository.findByUserId(userId);
        User user = users.get(0);
        //자소서 이름 중복확인 후 처리
        String fixedIntroName = generateUniqueIntroName(userId, introName);
        //자소서 저장
        SelfIntroduction selfIntroduction = SelfIntroduction.create(fixedIntroName, introContent, user, field);
        selfIntroRepository.save(selfIntroduction);

        return selfIntroduction;
    }

    /**
     * 자소서 하나 조회
     */
    public SelfIntroduction showOne(Long introId) {
        return selfIntroRepository.findOne(introId);
    }

    /**
     * user의 자소서 모두 조회
     * 사용법 : String userId = session.getAttribute(loginUser)
     */
    public List<SelfIntroduction> showIntroAll(String userId) {
        return selfIntroRepository.showAll(userId);
    }

    /**
     * user의 자소서 하나 수정
     */
    @Transactional
    public SelfIntroduction updateSelfIntro(String userId, Long introId, String introName, String introContent, int field) throws Exception {
        //selfIntro 조회
        SelfIntroduction selfIntroduction = selfIntroRepository.findOne(introId);
        if (selfIntroduction == null) {
            throw new Exception("자기소개서를 찾을 수 없습니다.");
        }
        //자소서 이름 중복확인 후 처리
        String fixedIntroName = generateUniqueIntroName(userId, introName);
        //자소서 업데이트
        selfIntroduction.update(fixedIntroName, introContent, field);
        selfIntroduction.updateSignal();
        return selfIntroduction;
    }

    /**
     * user 자소서 하나 삭제
     */
    @Transactional
    public int removeSelfIntro(Long introId){
        //selfIntro 조회
        SelfIntroduction selfIntroduction = selfIntroRepository.findOne(introId);
        //자소서 삭제
        selfIntroRepository.delete(selfIntroduction);
        return 1;
    }

    /**
     * IntroName 중복 확인
     */
    public String generateUniqueIntroName(String userId, String introName) {
        List<SelfIntroduction> existingIntros = selfIntroRepository.findByName(userId, introName);
        if (existingIntros.isEmpty()) {
            return introName;
        } else {
            return introName + " (" + existingIntros.size() + ")";
        }
    }

}
