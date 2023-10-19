import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CenterDetailComponent } from './center-detail.component';

describe('Center Management Detail Component', () => {
  let comp: CenterDetailComponent;
  let fixture: ComponentFixture<CenterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CenterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ center: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(CenterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CenterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load center on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.center).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
