import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudstatususuarioComponent } from './crudstatususuario.component';

describe('CrudstatususuarioComponent', () => {
  let component: CrudstatususuarioComponent;
  let fixture: ComponentFixture<CrudstatususuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CrudstatususuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrudstatususuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
