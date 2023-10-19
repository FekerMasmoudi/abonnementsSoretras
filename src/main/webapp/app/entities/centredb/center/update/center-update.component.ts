import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CenterFormService, CenterFormGroup } from './center-form.service';
import { ICenter } from '../center.model';
import { CenterService } from '../service/center.service';

@Component({
  selector: 'jhi-center-update',
  templateUrl: './center-update.component.html',
})
export class CenterUpdateComponent implements OnInit {
  isSaving = false;
  center: ICenter | null = null;

  editForm: CenterFormGroup = this.centerFormService.createCenterFormGroup();

  constructor(
    protected centerService: CenterService,
    protected centerFormService: CenterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ center }) => {
      this.center = center;
      if (center) {
        this.updateForm(center);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const center = this.centerFormService.getCenter(this.editForm);
    if (center.id !== null) {
      this.subscribeToSaveResponse(this.centerService.update(center));
    } else {
      this.subscribeToSaveResponse(this.centerService.create(center));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICenter>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(center: ICenter): void {
    this.center = center;
    this.centerFormService.resetForm(this.editForm, center);
  }
}
