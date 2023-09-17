package com.justAnotherVitor.MoneyFlow.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.justAnotherVitor.MoneyFlow.Repository.UserRepository;
import com.justAnotherVitor.MoneyFlow.Services.Exceptions.DatabaseException;
import com.justAnotherVitor.MoneyFlow.Services.Exceptions.ObjectNotFoundException;
import com.justAnotherVitor.MoneyFlow.Services.Exceptions.ResourceNotFoundException;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;


@Service
public class ServicesForUser {
	
	@Autowired
	private UserRepository repository;
	
	
	public List<UserEntity> findAll()
	{
		return repository.findAll();
	}
	

	public Optional<UserEntity> findById(String id)
	{
		Optional <UserEntity> user = repository.findById(id);
		return user;
	}
	
	
	public UserEntity insert(UserEntity obj)
	{
		return repository.insert(obj);
	}
	
	public void delete (String id)
	{
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Optional<UserEntity> update(String id, UserEntity obj)
	{
		
		Optional<UserEntity> entity = findById(id);
		entity.stream().filter(x -> x.getId().equals(id)).findAny().ifPresentOrElse(x->
		{x.setName(obj.getName());
		x.setLogin(obj.getLogin());
		x.setPassword(obj.getPassword());
		},
		()->{
			throw new ObjectNotFoundException("Objeto não encontrado");
		});
		
		return entity;
		}
	
}
