import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnotationCreationComponent } from './annotation-creation.component';

describe('AnnotationCreationComponent', () => {
  let component: AnnotationCreationComponent;
  let fixture: ComponentFixture<AnnotationCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnotationCreationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnnotationCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
