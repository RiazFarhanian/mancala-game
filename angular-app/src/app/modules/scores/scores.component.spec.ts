import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoresComponent } from './scores.component';

describe('ScoresComponent', () => {
  let component: ScoresComponent;
  let fixture: ComponentFixture<ScoresComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ScoresComponent]
    });
    fixture = TestBed.createComponent(ScoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
