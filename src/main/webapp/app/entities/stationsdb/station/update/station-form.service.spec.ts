import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../station.test-samples';

import { StationFormService } from './station-form.service';

describe('Station Form Service', () => {
  let service: StationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StationFormService);
  });

  describe('Service methods', () => {
    describe('createStationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            namefr: expect.any(Object),
            namear: expect.any(Object),
            longitude: expect.any(Object),
            lattitude: expect.any(Object),
            decstat: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IStation should create a new form with FormGroup', () => {
        const formGroup = service.createStationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            namefr: expect.any(Object),
            namear: expect.any(Object),
            longitude: expect.any(Object),
            lattitude: expect.any(Object),
            decstat: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getStation', () => {
      it('should return NewStation for default Station initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStationFormGroup(sampleWithNewData);

        const station = service.getStation(formGroup) as any;

        expect(station).toMatchObject(sampleWithNewData);
      });

      it('should return NewStation for empty Station initial value', () => {
        const formGroup = service.createStationFormGroup();

        const station = service.getStation(formGroup) as any;

        expect(station).toMatchObject({});
      });

      it('should return IStation', () => {
        const formGroup = service.createStationFormGroup(sampleWithRequiredData);

        const station = service.getStation(formGroup) as any;

        expect(station).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStation should not enable id FormControl', () => {
        const formGroup = service.createStationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStation should disable id FormControl', () => {
        const formGroup = service.createStationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
