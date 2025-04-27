package com.github.hanjungho.datespotbackend.service;

import com.github.hanjungho.datespotbackend.dto.*;
import com.github.hanjungho.datespotbackend.entity.AccountEntity;
import com.github.hanjungho.datespotbackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Oauth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        AccountEntity existData = accountRepository.findByUsernameOAuth2(username);

        if (existData == null) {

            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setUsernameOAuth2(username);
            accountEntity.setEmailOAuth2(oAuth2Response.getEmail());
            accountEntity.setNameOAuth2(oAuth2Response.getName());
            accountEntity.setRole("ROLE_USER");

            accountRepository.save(accountEntity);

            UserDTO userDTO = new UserDTO(oAuth2Response.getName(), username, "ROLE_USER");

            return new CustomOAuth2User(userDTO);
        } else {

            existData.setUsernameOAuth2(username);
            existData.setEmailOAuth2(oAuth2Response.getEmail());
            existData.setNameOAuth2(oAuth2Response.getName());

            accountRepository.save(existData);

            UserDTO userDTO = new UserDTO(oAuth2Response.getName(), username, existData.getRole());

            return new CustomOAuth2User(userDTO);
        }
    }
}
