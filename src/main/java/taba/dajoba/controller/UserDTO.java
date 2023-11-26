package taba.dajoba.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String nickName;
    private String birth;
    private String phoneNumber;
    private String email;

}
