package com.justAnotherVitor.MoneyFlow.domain;
/*Classe do documento notas da entidade de notas*/
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.justAnotherVitor.MoneyFlow.config.converters.ZonedDateTimeSerializer;
import com.justAnotherVitor.MoneyFlow.dto.AuthorDto;


@Document(collection ="notes")
public class NoteEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private String noteId;
	//Serializador customizado para ZonedDateTime
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private ZonedDateTime date = ZonedDateTime.now();
	/*Objeto base para a relação muitos um, e também anottation JsonIgnore para que o mesmo não seja
	escrito no JSON*/
	@JsonManagedReference
	@JsonIgnore
	private UserEntity user;
	//Um objeto de transferência de dados para evitar o uso direto da entidade de usuário
	private AuthorDto author;
	private Double money;
	private String tittle;
	private String description;
	
	public NoteEntity ()
	{
		
	}
	public NoteEntity(ZonedDateTime date,AuthorDto author,Double money, String tittle, String description) {
		super();
		this.date = date;
		this.author = author;
		this.money = money;
		this.tittle = tittle;
		this.description = description;
	}
	
	public NoteEntity(Double money, String tittle, String description) {
		super();
		this.money = money;
		this.tittle = tittle;
		this.description = description;
	}

	public NoteEntity(String noteId, ZonedDateTime date,AuthorDto author, Double money, String tittle, String description) {
		super();
		this.noteId = noteId;
		this.date = date;
		this.author = author;
		this.money = money;
		this.tittle = tittle;
		this.description = description;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	
	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}
	
	public UserEntity getUser() {
		return user;
	}
	
	
	public void setUser(UserEntity user)
	{
		this.user = user;
	}
	
	public AuthorDto getAuthor() {
		return author;
	}
	
	public void setAuthor(AuthorDto author) {
		this.author = author;
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
		NoteEntity other = (NoteEntity) obj;
		return Objects.equals(noteId, other.noteId);
	}
/*@Override
	public String toString() {
		return "NoteEntity [noteId=" + noteId + ", date=" + date + ", money=" + money + ", tittle=" + tittle
				+ ", description=" + description + "]";
	}
	
*/	
}
