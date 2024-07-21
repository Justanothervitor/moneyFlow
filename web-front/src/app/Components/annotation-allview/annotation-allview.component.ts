import { Component,OnInit } from '@angular/core';
import { AnnotationsService } from '../../Services&Helpers/_services/annotations.service';
import { StorageService } from '../../Services&Helpers/_services/storage.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-annotation-allview',
  templateUrl: './annotation-allview.component.html',
  styleUrl: './annotation-allview.component.scss'
})

export class AnnotationAllviewComponent implements OnInit{

  content?: any

  constructor(private annotation:AnnotationsService,private storage:StorageService){}

  ngOnInit(): void {

      this.annotation.getAllAnotations().subscribe({
        next : data=>{
          this.isNullOrNot(data);
          console.log(this.content);
        },
        error: err =>{ console.log(err);
          if(err.error){
            this.content = JSON.parse(err.error).message;
          }else{
            this.content = "Error with status" + err.status;
          }
        }
      })
  }
  isNullOrNot(data:any):any
  {
    if(data==="[]")
    {
      return this.content = ("Você não tem nenhuma anotação.\n Que tal criar uma?");
    }else{
      return this.content = data;
    }
  }
  /*onClick(event:MouseEvent):void{
    event.
  }*/
}