package com.justAnotherVitor.MoneyFlow.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.justAnotherVitor.MoneyFlow.domain.UserEntity;


public interface UserRepository extends MongoRepository <UserEntity,String>{

}
