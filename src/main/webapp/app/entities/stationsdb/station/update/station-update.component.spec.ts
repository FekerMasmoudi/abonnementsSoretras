import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StationFormService } from './station-form.service';
import { StationService } from '../service/station.service';
import { IStation } from '../station.model';

import { StationUpdateComponent } from './station-update.component';

describe('Station Management Update Component', () => {
  let comp: StationUpdateComponent;
  let fixture: ComponentFixture<StationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stationFormService: StationFormService;
  let stationService: StationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StationUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(StationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stationFormService = TestBed.inject(StationFormService);
    stationService = TestBed.inject(StationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const station: IStation = { id: 'CBA' };

      activatedRoute.data = of({ station });
      comp.ngOnInit();

      expect(comp.station).toEqual(station);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStation>>();
      const station = { id: 'ABC' };
      jest.spyOn(stationFormService, 'getStation').mockReturnValue(station);
      jest.spyOn(stationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ station });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: station }));
      saveSubject.complete();

      // THEN
      expect(stationFormService.getStation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stationService.update).toHaveBeenCalledWith(expect.objectContaining(station));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStation>>();
      const station = { id: 'ABC' };
      jest.spyOn(stationFormService, 'getStation').mockReturnValue({ id: null });
      jest.spyOn(stationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ station: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: station }));
      saveSubject.complete();

      // THEN
      expect(stationFormService.getStation).toHaveBeenCalled();
      expect(stationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStation>>();
      const station = { id: 'ABC' };
      jest.spyOn(stationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ station });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
