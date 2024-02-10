package com.Api.MoneyFlow.domains;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Document(collection="annotations")
public class AnnotationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String noteId;
	private String name;
	private Double value;
	private ZonedDateTime userInputData;
	private String description;
	private ZonedDateTime dateOfCreation = ZonedDateTime.now();
	
	@JsonIgnore
	@JsonManagedReference
	private UserDomain user;
	
	
	public AnnotationDomain(String noteId,String name,ZonedDateTime input,Double value,String description) {
		this.noteId = noteId;
		this.name = name;
		this.userInputData = input;
		this.value = value;
		this.description = description;
	}
	
	public AnnotationDomain(String name,String description,Double value)
	{
		this.name = name;
		this.description = description;
		this.value = value;
	}


	public String getNoteId() {
		return noteId;
	}


	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getValue() {
		return value;
	}


	public void setValue(Double value) {
		this.value = value;
	}


	public ZonedDateTime getUserInputData() {
		return userInputData;
	}


	public void setUserInputData(ZonedDateTime userInputData) {
		this.userInputData = userInputData;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public ZonedDateTime getDateOfCreation() {
		return dateOfCreation;
	}


	public void setDateOfCreation(ZonedDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}


	public UserDomain getUser() {
		return user;
	}


	public void setUser(UserDomain user) {
		this.user = user;
	}


	@Override
	public int hashCode() {
		return Objects.hash(noteId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnotationDomain other = (AnnotationDomain) obj;
		return Objects.equals(noteId, other.noteId);
	}
	
	

}
