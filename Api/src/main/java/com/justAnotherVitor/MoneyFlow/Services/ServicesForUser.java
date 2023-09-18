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
	
	public UserEntity update(String id, UserEntity obj)
	{
		UserEntity user = this.repository.findById(id).get();
		user.setName(obj.getName());
		user.setLogin(obj.getLogin());
		user.setPassword(obj.getPassword());
		
		return this.repository.save(user);
	}
		
		}
	
