package com.Api.MoneyFlow.Templates;

import java.util.List;

import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationPutRequest;
import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;

public interface AnnotationTemplate {
	
	List<AnnotationResponse> fetchAnnotationRecent();
	//List<AnnotationResponse> fetchAnnotationByName(String requestedName);
	AnnotationDomain fetchOneById(String id);
	AnnotationDomain createAnnotation(InputAnnotationRequest input);
	AnnotationDomain updateAnnotation(String id,InputAnnotationPutRequest input);
	void deleteAnnotation(String id);

}
