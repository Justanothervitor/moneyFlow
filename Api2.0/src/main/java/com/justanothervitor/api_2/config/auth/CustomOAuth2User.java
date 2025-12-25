package com.justanothervitor.api_2.config.auth;
import com.justanothervitor.api_2.models.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class CustomOAuth2User implements OAuth2User  {

    private Long id;
    private String email;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String,Object> attributes;

    public CustomOAuth2User(Long id, String email, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.authorities = authorities;
    }

    public static CustomOAuth2User create(User user){
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRoles().stream().findFirst()));
        return new CustomOAuth2User(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                authorities
        );
    }

    public static CustomOAuth2User create(User user, Map<String,Object> attributes)
    {
        CustomOAuth2User customOAuth2User = CustomOAuth2User.create(user);
        customOAuth2User.setAttributes(attributes);
        return customOAuth2User;
    }


    @Override
    public String getName() {
        return String.valueOf(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
