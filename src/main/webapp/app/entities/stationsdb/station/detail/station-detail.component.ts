import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStation } from '../station.model';

@Component({
  selector: 'jhi-station-detail',
  templateUrl: './station-detail.component.html',
})
export class StationDetailComponent implements OnInit {
  station: IStation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ station }) => {
      this.station = station;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
