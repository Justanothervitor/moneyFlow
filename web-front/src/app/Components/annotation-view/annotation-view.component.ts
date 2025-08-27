import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import { AnnotationsService } from '../../ServicesAndHelpers/_services/annotations.service';
import {UpdateAnnotation} from "../../Models/formUpdateAnnotation";
import {Annotation} from "../../Models/Annotation";




@Component({
  selector: 'app-annotation-view',
  templateUrl: './annotation-view.component.html',
  styleUrl: './annotation-view.component.scss'
})
export class AnnotationViewComponent implements OnInit{
  @Output() annotationEvent = new EventEmitter<any>;
  @Input() id :any;
  isLoading= false;
  content?:Annotation | null;
  wasError = false;
  enableUpdate = false;
  message?:string;
  enableDelete = false;

  form : UpdateAnnotation ={
    name : "",
    value: 0,
    description: ""
  };

  constructor(protected annotationServices:AnnotationsService){}


  ngOnInit() {
    console.log(this.id);
  if(this.id != null){
    this.annotationServices.getOnlyOneAnotation(this.id).subscribe({
      next: data => {
        this.content = data;
        this.wasError = false;
      },error: err => {
        this.wasError = true;
        this.content = err;
      }
    })
  }
  }

  onSubmitHandleUpdate(id:string)
  {
    const request = this.form;
    this.isLoading = true;
    this.annotationServices.updateAnotation(id,request).subscribe({
      next:data =>{
        this.content = data;
        this.message = "Anotação atualizada com sucesso!";
        this.enableUpdate = false;
        this.isLoading = false;
      },error: err => {
        this.wasError = true;
        this.message = err;
        this.isLoading = false;
      }
    })
  }

  onSubmitHandleDelete(id:any)
  {
    this.isLoading = true;
    this.annotationServices.deleteAnotation(id).subscribe({
      next: data => {
        this.isLoading = false;
        window.location.reload();
      },error: err => {
        this.wasError = true;
        this.message = err;
        this.isLoading = false;
      }
    })
  }
}
