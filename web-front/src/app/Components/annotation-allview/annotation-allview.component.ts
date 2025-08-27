import {AfterViewInit, Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import { AnnotationsService } from '../../ServicesAndHelpers/_services/annotations.service';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import {Annotation} from "../../Models/Annotation";

@Component({
  selector: 'app-annotation-allview',
  templateUrl: './annotation-allview.component.html',
  styleUrl: './annotation-allview.component.scss'
})

export class AnnotationAllviewComponent implements OnInit{

  selectedAnnotation?: Annotation | null;
  content?: any;
  isLoading = false;

  constructor(protected annotationService: AnnotationsService, protected storageService: StorageService) {
  }

  ngOnInit() {
    this.isLoading = true;
    if(!this.storageService.isLoggedIn()){
      this.content = "Você precisa estar logado para usar essa função!";
      console.log(this.content);
      this.isLoading = false;
    }else{
      this.annotationService.getAllAnotations().subscribe(
        {
          next: data => {
            if(!data){
              this.content = "Você não tem nenhuma anotação, que tal criar uma?"
              this.isLoading = false;
            }else{
              this.content = data;
              this.isLoading = false;
              console.log(this.content);
            }
          },error: err => {
            this.content = err;
            this.isLoading = false;
          }
        }
      )
    }
  }

  verifyContent(content:any):boolean{
    return content && content.length > 0;

  }

  onSelect($event:any){
   this.selectedAnnotation = $event;
  }
}
