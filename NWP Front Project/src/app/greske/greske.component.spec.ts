import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GreskeComponent } from './greske.component';

describe('GreskeComponent', () => {
  let component: GreskeComponent;
  let fixture: ComponentFixture<GreskeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GreskeComponent]
    });
    fixture = TestBed.createComponent(GreskeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
