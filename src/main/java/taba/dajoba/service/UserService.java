package taba.dajoba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.dajoba.controller.UserDTO;
import taba.dajoba.domain.User;
import taba.dajoba.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional
    public void join(UserDTO userDTO) {
        //중복회원검사
        validateDuplicateMember(userDTO);
        //dto->entity변환
        User userEntity = User.toUserEntity(userDTO);
        //저장작업
        userRepository.save(userEntity);
    }

    private void validateDuplicateMember(UserDTO userDTO) {
        List<User> findUsers = userRepository.findByUserId(userDTO.getUserId());
        if( !findUsers.isEmpty() ){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public UserDTO login(UserDTO userDTO) {
        List<User> findUsers = userRepository.findByUserId(userDTO.getUserId());
        Optional<User> byUserId = Optional.of(findUsers.get(0)); //일치하는 하나의 user를 조회
        if(byUserId.isPresent()){
            //아이디 존재O
            User user = byUserId.get();
            if(user.getPassword().equals(userDTO.getPassword())){
                //비밀번호 일치
                return User.toUserDTO(user);
            } else {
                //비밀번호 불일치
                return null;
            }
        } else {
            //아이디 존재X
            return null;
        }

    }
}
