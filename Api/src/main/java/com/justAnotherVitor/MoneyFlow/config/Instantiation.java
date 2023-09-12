package com.justAnotherVitor.MoneyFlow.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.justAnotherVitor.MoneyFlow.Repository.UserRepository;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

@Configuration
public class Instantiation implements CommandLineRunner{

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		userRepository.deleteAll();
		
		UserEntity joao = new UserEntity(null,"João de Ipiracança","thriduser","anotheronebites");
		UserEntity bob = new UserEntity(null,"Bob Marley","fourthuser","anothersampletext");
		UserEntity julie = new UserEntity(null,"Julie dos Santos","fiftyuser","anothersamplepass");
		
		userRepository.saveAll(Arrays.asList(joao,bob,julie));
		
		
	}

}
