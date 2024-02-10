package com.Api.MoneyFlow.Templates;

import java.util.List;

import com.Api.MoneyFlow.domains.AnnotationDomain;

public interface AnnotationTemplate {
	
	List<AnnotationDomain> fetchAnnotations();
	List<AnnotationDomain> fecthAnnotationRecent();
	AnnotationDomain fetchOneById(String id);
	AnnotationDomain createAnnotation(AnnotationDomain obj);
	AnnotationDomain updateAnnotation(String id,AnnotationDomain obj);
	void deleteAnnotation(String id);

}
