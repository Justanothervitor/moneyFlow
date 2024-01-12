import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardEnterpressComponent } from './board-enterpress.component';

describe('BoardEnterpressComponent', () => {
  let component: BoardEnterpressComponent;
  let fixture: ComponentFixture<BoardEnterpressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoardEnterpressComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoardEnterpressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
