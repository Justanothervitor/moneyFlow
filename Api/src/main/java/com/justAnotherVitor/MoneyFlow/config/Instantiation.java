package com.justAnotherVitor.MoneyFlow.config;
//Classe de injeção de dados no banco de dados
import java.time.ZonedDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.justAnotherVitor.MoneyFlow.Repository.NotesRepository;
import com.justAnotherVitor.MoneyFlow.Repository.UserRepository;
import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;
import com.justAnotherVitor.MoneyFlow.dto.AuthorDto;

@Configuration
public class Instantiation implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotesRepository noteRepository;
	
	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		noteRepository.deleteAll();
		
		UserEntity u0 = new UserEntity(null,"João de Ipiracança","thriduser","anotheronebites");
		UserEntity u1 = new UserEntity(null,"Bob Marley","fourthuser","anothersampletext");
		UserEntity u2 = new UserEntity(null,"Julie dos Santos","fiftyuser","anothersamplepass");
		userRepository.saveAll(Arrays.asList(u0,u1,u2));
		
		NoteEntity n1 = new NoteEntity(null,ZonedDateTime.parse("2023-08-27T16:35:08Z"),new AuthorDto(u0),350.0,"SuperMercado","Compra de mantimentos");
		NoteEntity n2 = new NoteEntity(null,ZonedDateTime.parse("2023-08-28T11:30:00Z"),new AuthorDto(u0),120.0,"Abastecimento do carro","Abastecimento de 40 Litros de gasolina");
		NoteEntity n3 = new NoteEntity(null,ZonedDateTime.parse("2023-08-27T14:00:00Z"),new AuthorDto(u1),50.0,"Peças","Compra de peças para conserto de um eletronico");
		NoteEntity n4 = new NoteEntity(null,ZonedDateTime.parse("2023-08-31T20:30:00Z"),new AuthorDto(u1),4350.0,"Hardware","Compra de peças de computador");
		NoteEntity n5 = new NoteEntity(null,ZonedDateTime.parse("2023-08-27T20:00:00Z"),new AuthorDto(u2),110.0,"Conta de energia","Pagamento da conta de energia");
		noteRepository.saveAll(Arrays.asList(n1,n2,n3,n4,n5));
		
		u0.getNotes().addAll(Arrays.asList(n1,n2));
		u1.getNotes().addAll(Arrays.asList(n3,n4));
		u2.getNotes().addAll(Arrays.asList(n5));
		userRepository.saveAll(Arrays.asList(u0,u1,u2));
		
	}
	

}
