import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewgameComponent } from './newgame.component';

describe('NewgameComponent', () => {
  let component: NewgameComponent;
  let fixture: ComponentFixture<NewgameComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewgameComponent]
    });
    fixture = TestBed.createComponent(NewgameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
