import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudsucursalesComponent } from './crudsucursales.component';

describe('CrudsucursalesComponent', () => {
  let component: CrudsucursalesComponent;
  let fixture: ComponentFixture<CrudsucursalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CrudsucursalesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrudsucursalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
