package com.justanothervitor.api_2.models.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNotePayload {

    private long id;
    private String name;
    private String description;
    private float price;

    public UpdateNotePayload(long id, String name, String description, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
