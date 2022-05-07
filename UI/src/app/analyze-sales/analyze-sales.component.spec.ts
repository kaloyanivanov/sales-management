import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyzeSalesComponent } from './analyze-sales.component';

describe('AnalyzeSalesComponent', () => {
  let component: AnalyzeSalesComponent;
  let fixture: ComponentFixture<AnalyzeSalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnalyzeSalesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalyzeSalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
