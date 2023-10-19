import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StationDetailComponent } from './station-detail.component';

describe('Station Management Detail Component', () => {
  let comp: StationDetailComponent;
  let fixture: ComponentFixture<StationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ station: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(StationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load station on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.station).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
