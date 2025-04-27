package com.github.hanjungho.datespotbackend.repository;

import com.github.hanjungho.datespotbackend.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsByUsername(String username);

    AccountEntity findByUsernameOAuth2(String usernameOAuth2);
}
