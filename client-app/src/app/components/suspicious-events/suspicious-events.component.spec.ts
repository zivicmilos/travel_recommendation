import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuspiciousEventsComponent } from './suspicious-events.component';

describe('SuspiciousEventsComponent', () => {
  let component: SuspiciousEventsComponent;
  let fixture: ComponentFixture<SuspiciousEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuspiciousEventsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuspiciousEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
