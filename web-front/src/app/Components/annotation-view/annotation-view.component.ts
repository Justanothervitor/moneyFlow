import { Component, OnInit } from '@angular/core';
import { StorageService } from '../../Services&Helpers/_services/storage.service';
import { AnnotationsService } from '../../Services&Helpers/_services/annotations.service';

@Component({
  selector: 'app-annotation-view',
  templateUrl: './annotation-view.component.html',
  styleUrl: './annotation-view.component.scss'
})
export class AnnotationViewComponent implements OnInit{

  content? :string;
  requiredAnnotation?: string;

  isThereAnyAnnotation = false;

  constructor(private storage: StorageService,private annotations:AnnotationsService){}

  ngOnInit(): void {
      if(this.storage.isLoggedIn())
      {
        
      }
  }
}
