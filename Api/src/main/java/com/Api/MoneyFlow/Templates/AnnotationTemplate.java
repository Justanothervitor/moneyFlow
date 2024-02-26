package com.Api.MoneyFlow.Templates;

import java.util.List;

import com.Api.MoneyFlow.domains.AnnotationDomain;
import com.Api.MoneyFlow.payloads.request.InputAnnotationRequest;

public interface AnnotationTemplate {
	
	List<AnnotationDomain> fecthAnnotationRecent();
	AnnotationDomain fetchOneById(String id);
	AnnotationDomain createAnnotation(InputAnnotationRequest input);
	AnnotationDomain updateAnnotation(String id,InputAnnotationRequest input);
	void deleteAnnotation(String id);

}
