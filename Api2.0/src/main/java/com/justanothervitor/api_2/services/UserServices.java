package com.justanothervitor.api_2.services;

import com.justanothervitor.api_2.exceptions.NotFoundException;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.payloads.request.UserUpdatePayload;
import com.justanothervitor.api_2.repositories.UserRepositories;
import org.springframework.stereotype.Service;


@Service
public class UserServices {

    protected UserRepositories userRepositories;

    public UserServices(UserRepositories userRepositories) {this.userRepositories = userRepositories;}

    public void updateUser(long id, UserUpdatePayload valueToUpdate) {
        User obj = this.userRepositories.findById(id).orElseThrow();
        if(valueToUpdate.getUsername() != null) {
            if(obj.getUsername().equals(valueToUpdate.getUsername())) {
                obj.setUsername(valueToUpdate.getUsername());
            }
        }
        if(valueToUpdate.getEmail() != null) {
            if(obj.getEmail().equals(valueToUpdate.getEmail())) {
                obj.setEmail(valueToUpdate.getEmail());
            }
        }
        this.userRepositories.save(obj);
    }

}
