package com.justanothervitor.api_2.models;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static Oauth2InfoClass getOAuth2UserInfo(String registrationId, Map<String,Object> attributes)
    {
        if(registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString()))
        {
            return new GoogleOauth2InfoClass(attributes);
        }else{
            throw new RuntimeException("Login com id: "+registrationId+" inválido, pois esse tipo de login não é suportado!");
        }
    }
}
