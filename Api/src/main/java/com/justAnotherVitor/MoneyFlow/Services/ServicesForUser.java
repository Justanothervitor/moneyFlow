package com.justAnotherVitor.MoneyFlow.Services;

import java.util.List;
//import java.util.Optional;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.justAnotherVitor.MoneyFlow.Repository.UserRepository;
import com.justAnotherVitor.MoneyFlow.Services.Exceptions.ObjectNotFoundException;
//import com.justAnotherVitor.MoneyFlow.Services.Exceptions.DatabaseException;
//import com.justAnotherVitor.MoneyFlow.Services.Exceptions.ResourceNotFoundException;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

//import jakarta.persistence.EntityNotFoundException;

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
		
		if(user.isEmpty())
		{
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
		return user;
	}
	
	/*
	public UserEntity insert(UserEntity obj)
	{
		return repository.save(obj);
	}
	
	public void delete (Long id)
	{
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public UserEntity  update(Long id, UserEntity obj)
	{
		try {
			UserEntity entity = repository.getById(id);
			updateData(entity,obj);
			return repository.save(entity);
		}catch(EntityNotFoundException e){
			throw new ResourceNotFoundException(id);
		}
		
	}
	
	private void updateData(UserEntity entity, UserEntity obj)
	{
		entity.setLogin(obj.getLogin());
		entity.setName(obj.getName());
		entity.setPassword(obj.getPassword());
	}
	*/
}
