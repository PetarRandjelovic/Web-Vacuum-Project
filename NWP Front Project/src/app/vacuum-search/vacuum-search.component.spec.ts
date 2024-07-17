import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacuumSearchComponent } from './vacuum-search.component';

describe('VacuumSearchComponent', () => {
  let component: VacuumSearchComponent;
  let fixture: ComponentFixture<VacuumSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VacuumSearchComponent]
    });
    fixture = TestBed.createComponent(VacuumSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
