import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveSaleComponent } from './remove-sale.component';

describe('RemoveSaleComponent', () => {
  let component: RemoveSaleComponent;
  let fixture: ComponentFixture<RemoveSaleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RemoveSaleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveSaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
