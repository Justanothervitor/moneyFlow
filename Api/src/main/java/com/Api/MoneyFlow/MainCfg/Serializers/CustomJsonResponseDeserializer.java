package com.Api.MoneyFlow.MainCfg.Serializers;

import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomJsonResponseDeserializer extends JsonDeserializer<AnnotationResponse> {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Override
    public AnnotationResponse deserialize (JsonParser p, DeserializationContext context) throws IOException {

        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        String id = node.get("noteId").textValue();
        String name = node.get("name").textValue();
        Double value = node.get("value").doubleValue();
        ZonedDateTime userInputData = ZonedDateTime.parse(node.get("userInputData").textValue(),FMT);
        String description = node.get("description").textValue();
        ZonedDateTime dateOfCreation = ZonedDateTime.parse(node.get("dateOfCreation").textValue(),FMT);

        return new AnnotationResponse(new AnnotationDomain(id,name,value,userInputData,description,dateOfCreation));
    }

}
