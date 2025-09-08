package com.Api.MoneyFlow.Templates;

import java.util.List;

import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.Api.MoneyFlow.Domains.UserDomain;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationPutRequest;
import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;

public interface AnnotationTemplate {
	
	List<AnnotationResponse> fetchAnnotationRecent(UserDomain requested);
	//List<AnnotationResponse> fetchAnnotationByName(String requestedName);
	AnnotationDomain fetchOneById(String id);
	void createAnnotation(InputAnnotationRequest input,UserDomain requested);
	void updateAnnotation(String id, InputAnnotationPutRequest input);
	void deleteAnnotation(String id);

}
