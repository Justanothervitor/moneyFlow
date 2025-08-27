package com.Api.MoneyFlow.Domains;

import com.Api.MoneyFlow.MainCfg.Serializers.CustomJsonResponseDeserializer;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Data
@Document(collection="Annotations")
@JsonDeserialize(using= CustomJsonResponseDeserializer.class)
public class AnnotationDomain implements Serializable {
	
	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	protected String noteId;
	protected String name;
	protected Double value;
	protected ZonedDateTime userInputData;
	@JsonIgnore
	protected String originalTimeZone;
	protected String description;
	protected ZonedDateTime dateOfCreation = ZonedDateTime.now();
	
	@JsonIgnore
	@JsonManagedReference
	@DocumentReference
	protected UserDomain user;

	public AnnotationDomain(@NotNull InputAnnotationRequest req, @NotNull UserDomain owner)
	{
		this.name = req.getName();
		this.value = req.getValue();
		if(req.getInputDate() != null)
		{
			this.originalTimeZone = req.getInputDate();
			this.userInputData = ZonedDateTime.parse(req.getInputDate(),FMT);
		}
		this.description = req.getDescription();
		this.user = owner;
	}

	public AnnotationDomain(final String noteId,final String name,final Double value,final ZonedDateTime userInputData,final String description,final ZonedDateTime dateOfCreation) {
		this.noteId = noteId;
		this.name = name;
		this.value = value;
		this.userInputData = userInputData;
		this.description = description;
		this.dateOfCreation = dateOfCreation;
	}

	public AnnotationDomain(){}

}
