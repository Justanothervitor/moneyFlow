import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnotationAllviewComponent } from './annotation-allview.component';

describe('AnnotationAllviewComponent', () => {
  let component: AnnotationAllviewComponent;
  let fixture: ComponentFixture<AnnotationAllviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnotationAllviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnnotationAllviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
