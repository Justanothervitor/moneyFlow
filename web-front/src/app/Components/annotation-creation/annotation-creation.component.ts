import { Component,OnInit,LOCALE_ID,Inject } from '@angular/core';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import { AnnotationsService } from '../../ServicesAndHelpers/_services/annotations.service';
import { formatDate, getLocaleId } from '@angular/common';
import {CreateAnnotation} from "../../Models/formCreateAnnotation";

@Component({
  selector: 'app-annotation-creation',
  templateUrl: './annotation-creation.component.html',
  styleUrl: './annotation-creation.component.scss'
})
export class AnnotationCreationComponent implements OnInit{

  form : CreateAnnotation ={
    name : "",
    value: 0,
    userInputDate: "",
    description: ""
  };

  isLoggedIn = false;
  isAnnotationCreationFailed = false;
  errorMessage ='';

  constructor(private annotation:AnnotationsService,private storage:StorageService,@Inject(LOCALE_ID)protected locale:string){}

  ngOnInit(): void {
      if(this.storage.isLoggedIn()){
        this.isLoggedIn = true;
      }
  }

  onSubmit():void{
    const request = this.form;
    const NRequest : CreateAnnotation ={
      name : request.name,
      value : request.value,
      userInputDate : formatDate(request.userInputDate,"yyyy-MM-dd'T'HH:mm:ss.SSSZ",this.locale),
      description : request.description
    }
    this.annotation.createAnnotation(NRequest).subscribe({
      next: data =>{
        console.log(data);
        window.location.reload();
      },
      error: err =>{
        this.errorMessage = err.error.message;
        this.isAnnotationCreationFailed = true;
      }
    });
  }
}
