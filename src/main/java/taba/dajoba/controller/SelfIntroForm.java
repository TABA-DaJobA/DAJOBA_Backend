package taba.dajoba.controller;

import lombok.*;
import taba.dajoba.domain.User;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class SelfIntroForm {

    private Long introId;
    private String introName;
    private String introContent;
    private User user;
    private LocalDate lastUpdated;
    private int desireField;

}