package com.justAnotherVitor.MoneyFlow.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection ="notes")
public class NoteEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private UserEntity user;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy 'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant date;
	private Double money;
	private String tittle;
	private String description;
	
	public NoteEntity ()
	{
		
	}

	public NoteEntity(String id, UserEntity user, Instant date, Double money, String tittle, String description) {
		super();
		this.id = id;
		this.user = user;
		this.date = date;
		this.money = money;
		this.tittle = tittle;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoteEntity other = (NoteEntity) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
