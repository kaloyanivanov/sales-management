import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveAgentComponent } from './remove-agent.component';

describe('RemoveAgentComponent', () => {
  let component: RemoveAgentComponent;
  let fixture: ComponentFixture<RemoveAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RemoveAgentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
