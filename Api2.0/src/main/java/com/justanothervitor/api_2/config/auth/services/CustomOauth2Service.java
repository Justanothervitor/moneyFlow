package com.justanothervitor.api_2.config.auth.services;

import com.justanothervitor.api_2.config.auth.CustomOAuth2User;
import com.justanothervitor.api_2.models.*;
import com.justanothervitor.api_2.repositories.RoleRepositories;
import com.justanothervitor.api_2.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomOauth2Service extends DefaultOAuth2UserService {

    @Autowired
    protected UserRepositories userRepositories;

    @Autowired
    protected RoleRepositories roleRepositories;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuthUser = super.loadUser(userRequest);
        try{
            return processOAuth2User(userRequest,oAuthUser);
        }catch (Exception e)
        {
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User user)
    {
        String registrationId = request.getClientRegistration().getRegistrationId();

        Oauth2InfoClass oauth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,user.getAttributes());

        if(oauth2UserInfo.getEmail().isEmpty())
        {
            throw new RuntimeException("Email não encontrado no provider OAuth2");
        }

        Optional<User> userOptional = userRepositories.findByEmail(oauth2UserInfo.getEmail());
        User userRequested;

        if(userOptional.isPresent())
        {
            userRequested = userOptional.get();

            if(!userRequested.getProvider().toString().equalsIgnoreCase(registrationId)){
                throw new RuntimeException("Você já se cadrastrou com "+userRequested.getProvider()+". Por favor, use esse método para fazer login");
            }
        }else{
            userRequested = registerNewUser(request,oauth2UserInfo);
        }
        return CustomOAuth2User.create(userRequested,user.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest request, Oauth2InfoClass oauth2Info)
    {
        User user = new User();
        Set<Role> roles  = new HashSet<>();
        roles.add(roleRepositories.findRoleByName("NORMAL"));
        user.setProvider(AuthProvider.valueOf(request.getClientRegistration().getRegistrationId().toUpperCase()));
        user.setProviderId(oauth2Info.getId());
        user.setUsername(oauth2Info.getEmail().split("@")[0]+"-"+System.currentTimeMillis());
        user.setEmail(oauth2Info.getEmail());
        user.setEmailVerified(true);
        user.setRoles(roles);
        user.setEnabled(true);

        return userRepositories.save(user);
    }

}
