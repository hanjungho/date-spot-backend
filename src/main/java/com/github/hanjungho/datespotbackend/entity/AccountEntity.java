package com.github.hanjungho.datespotbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String role;

    @Column
    private String emailOAuth2;

    @Column
    private String usernameOAuth2;

    @Column
    private String nameOAuth2;
}
