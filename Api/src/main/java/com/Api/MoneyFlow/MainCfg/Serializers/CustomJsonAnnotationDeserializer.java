package com.Api.MoneyFlow.MainCfg.Serializers;

import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CustomJsonAnnotationDeserializer extends JsonDeserializer<InputAnnotationRequest> {

    @Override
    public InputAnnotationRequest deserialize (JsonParser p, DeserializationContext context) throws IOException {

        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        String name = node.get("name").textValue();
        Double value = node.get("value").doubleValue();
        String userInputData = node.get("userInputDate").textValue();
        String description = node.get("description").textValue();

        return new InputAnnotationRequest(name,value,userInputData,description);
    }
}
