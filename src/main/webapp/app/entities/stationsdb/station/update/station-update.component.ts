import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StationFormService, StationFormGroup } from './station-form.service';
import { IStation } from '../station.model';
import { StationService } from '../service/station.service';

@Component({
  selector: 'jhi-station-update',
  templateUrl: './station-update.component.html',
})
export class StationUpdateComponent implements OnInit {
  isSaving = false;
  station: IStation | null = null;

  editForm: StationFormGroup = this.stationFormService.createStationFormGroup();

  constructor(
    protected stationService: StationService,
    protected stationFormService: StationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ station }) => {
      this.station = station;
      if (station) {
        this.updateForm(station);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const station = this.stationFormService.getStation(this.editForm);
    if (station.id !== null) {
      this.subscribeToSaveResponse(this.stationService.update(station));
    } else {
      this.subscribeToSaveResponse(this.stationService.create(station));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStation>>): void {
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

  protected updateForm(station: IStation): void {
    this.station = station;
    this.stationFormService.resetForm(this.editForm, station);
  }
}
