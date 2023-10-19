import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../center.test-samples';

import { CenterFormService } from './center-form.service';

describe('Center Form Service', () => {
  let service: CenterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CenterFormService);
  });

  describe('Service methods', () => {
    describe('createCenterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCenterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            deccent: expect.any(Object),
            delcent: expect.any(Object),
            delcentfr: expect.any(Object),
            deadrce: expect.any(Object),
            deobser: expect.any(Object),
          })
        );
      });

      it('passing ICenter should create a new form with FormGroup', () => {
        const formGroup = service.createCenterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            deccent: expect.any(Object),
            delcent: expect.any(Object),
            delcentfr: expect.any(Object),
            deadrce: expect.any(Object),
            deobser: expect.any(Object),
          })
        );
      });
    });

    describe('getCenter', () => {
      it('should return NewCenter for default Center initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCenterFormGroup(sampleWithNewData);

        const center = service.getCenter(formGroup) as any;

        expect(center).toMatchObject(sampleWithNewData);
      });

      it('should return NewCenter for empty Center initial value', () => {
        const formGroup = service.createCenterFormGroup();

        const center = service.getCenter(formGroup) as any;

        expect(center).toMatchObject({});
      });

      it('should return ICenter', () => {
        const formGroup = service.createCenterFormGroup(sampleWithRequiredData);

        const center = service.getCenter(formGroup) as any;

        expect(center).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICenter should not enable id FormControl', () => {
        const formGroup = service.createCenterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCenter should disable id FormControl', () => {
        const formGroup = service.createCenterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
