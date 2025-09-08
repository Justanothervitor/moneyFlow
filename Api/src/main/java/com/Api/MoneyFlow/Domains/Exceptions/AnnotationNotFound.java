package com.Api.MoneyFlow.Domains.Exceptions;

public class AnnotationNotFound extends Exception {
    public AnnotationNotFound(String id) {
        super(String.format("Annotation with id %s not found", id));
    }
}
