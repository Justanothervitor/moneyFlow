import { Component,OnInit,LOCALE_ID,Inject } from '@angular/core';
import { StorageService } from '../../Services&Helpers/_services/storage.service';
import { AnnotationsService } from '../../Services&Helpers/_services/annotations.service';
import { formatDate, getLocaleId } from '@angular/common';

@Component({
  selector: 'app-annotation-creation',
  templateUrl: './annotation-creation.component.html',
  styleUrl: './annotation-creation.component.scss'
})
export class AnnotationCreationComponent implements OnInit{

  form : any ={
    name : null,
    value: null,
    date: null,
    description: null
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
    const { name, value,date, description } = this.form;
    const userInputDate = formatDate(date,"yyyy-MM-dd'T'HH:mm:ss.SSSZ",this.locale);
    this.annotation.createAnnotation(name,value,userInputDate,description).subscribe({
      next: data =>{
        console.log(data);
        window.location.reload;
      },
      error: err =>{
        this.errorMessage = err.error.message;
        this.isAnnotationCreationFailed = true;
      }
    });
  }
}