import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStation, NewStation } from '../station.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStation for edit and NewStationFormGroupInput for create.
 */
type StationFormGroupInput = IStation | PartialWithRequiredKeyOf<NewStation>;

type StationFormDefaults = Pick<NewStation, 'id'>;

type StationFormGroupContent = {
  id: FormControl<IStation['id'] | NewStation['id']>;
  namefr: FormControl<IStation['namefr']>;
  namear: FormControl<IStation['namear']>;
  longitude: FormControl<IStation['longitude']>;
  lattitude: FormControl<IStation['lattitude']>;
  decstat: FormControl<IStation['decstat']>;
  status: FormControl<IStation['status']>;
};

export type StationFormGroup = FormGroup<StationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StationFormService {
  createStationFormGroup(station: StationFormGroupInput = { id: null }): StationFormGroup {
    const stationRawValue = {
      ...this.getFormDefaults(),
      ...station,
    };
    return new FormGroup<StationFormGroupContent>({
      id: new FormControl(
        { value: stationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      namefr: new FormControl(stationRawValue.namefr, {
        validators: [Validators.required],
      }),
      namear: new FormControl(stationRawValue.namear, {
        validators: [Validators.required],
      }),
      longitude: new FormControl(stationRawValue.longitude),
      lattitude: new FormControl(stationRawValue.lattitude),
      decstat: new FormControl(stationRawValue.decstat, {
        validators: [Validators.required],
      }),
      status: new FormControl(stationRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getStation(form: StationFormGroup): IStation | NewStation {
    return form.getRawValue() as IStation | NewStation;
  }

  resetForm(form: StationFormGroup, station: StationFormGroupInput): void {
    const stationRawValue = { ...this.getFormDefaults(), ...station };
    form.reset(
      {
        ...stationRawValue,
        id: { value: stationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StationFormDefaults {
    return {
      id: null,
    };
  }
}
