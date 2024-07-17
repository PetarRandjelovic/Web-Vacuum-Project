import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateVacuumComponent } from './create-vacuum.component';

describe('CreateVacuumComponent', () => {
  let component: CreateVacuumComponent;
  let fixture: ComponentFixture<CreateVacuumComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateVacuumComponent]
    });
    fixture = TestBed.createComponent(CreateVacuumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
