package com.justAnotherVitor.MoneyFlow.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

@Repository
public interface UserRepository extends MongoRepository <UserEntity,String>{

}
