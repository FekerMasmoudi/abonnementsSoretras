import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICenter, NewCenter } from '../center.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICenter for edit and NewCenterFormGroupInput for create.
 */
type CenterFormGroupInput = ICenter | PartialWithRequiredKeyOf<NewCenter>;

type CenterFormDefaults = Pick<NewCenter, 'id'>;

type CenterFormGroupContent = {
  id: FormControl<ICenter['id'] | NewCenter['id']>;
  deccent: FormControl<ICenter['deccent']>;
  delcent: FormControl<ICenter['delcent']>;
  delcentfr: FormControl<ICenter['delcentfr']>;
  deadrce: FormControl<ICenter['deadrce']>;
  deobser: FormControl<ICenter['deobser']>;
};

export type CenterFormGroup = FormGroup<CenterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CenterFormService {
  createCenterFormGroup(center: CenterFormGroupInput = { id: null }): CenterFormGroup {
    const centerRawValue = {
      ...this.getFormDefaults(),
      ...center,
    };
    return new FormGroup<CenterFormGroupContent>({
      id: new FormControl(
        { value: centerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      deccent: new FormControl(centerRawValue.deccent, {
        validators: [Validators.required],
      }),
      delcent: new FormControl(centerRawValue.delcent),
      delcentfr: new FormControl(centerRawValue.delcentfr),
      deadrce: new FormControl(centerRawValue.deadrce),
      deobser: new FormControl(centerRawValue.deobser),
    });
  }

  getCenter(form: CenterFormGroup): ICenter | NewCenter {
    return form.getRawValue() as ICenter | NewCenter;
  }

  resetForm(form: CenterFormGroup, center: CenterFormGroupInput): void {
    const centerRawValue = { ...this.getFormDefaults(), ...center };
    form.reset(
      {
        ...centerRawValue,
        id: { value: centerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CenterFormDefaults {
    return {
      id: null,
    };
  }
}
