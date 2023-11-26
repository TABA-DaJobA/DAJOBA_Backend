package taba.dajoba.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import taba.dajoba.controller.UserDTO;
import taba.dajoba.domain.User;
import taba.dajoba.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("user1");
        userDTO.setPassword("pwd");
        userDTO.setName("name");

        //when
        userService.join(userDTO);

        //then
        User savedUser = userRepository.findByUserId("user1").get(0);
        User userEntity = User.toUserEntity(userDTO);
        assertEquals(savedUser, userEntity);

    }
}