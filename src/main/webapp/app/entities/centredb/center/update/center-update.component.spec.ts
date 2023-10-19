import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CenterFormService } from './center-form.service';
import { CenterService } from '../service/center.service';
import { ICenter } from '../center.model';

import { CenterUpdateComponent } from './center-update.component';

describe('Center Management Update Component', () => {
  let comp: CenterUpdateComponent;
  let fixture: ComponentFixture<CenterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centerFormService: CenterFormService;
  let centerService: CenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CenterUpdateComponent],
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
      .overrideTemplate(CenterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CenterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centerFormService = TestBed.inject(CenterFormService);
    centerService = TestBed.inject(CenterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const center: ICenter = { id: 'CBA' };

      activatedRoute.data = of({ center });
      comp.ngOnInit();

      expect(comp.center).toEqual(center);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenter>>();
      const center = { id: 'ABC' };
      jest.spyOn(centerFormService, 'getCenter').mockReturnValue(center);
      jest.spyOn(centerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ center });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: center }));
      saveSubject.complete();

      // THEN
      expect(centerFormService.getCenter).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centerService.update).toHaveBeenCalledWith(expect.objectContaining(center));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenter>>();
      const center = { id: 'ABC' };
      jest.spyOn(centerFormService, 'getCenter').mockReturnValue({ id: null });
      jest.spyOn(centerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ center: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: center }));
      saveSubject.complete();

      // THEN
      expect(centerFormService.getCenter).toHaveBeenCalled();
      expect(centerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenter>>();
      const center = { id: 'ABC' };
      jest.spyOn(centerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ center });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
