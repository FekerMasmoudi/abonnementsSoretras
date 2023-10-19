import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StationComponent } from './list/station.component';
import { StationDetailComponent } from './detail/station-detail.component';
import { StationUpdateComponent } from './update/station-update.component';
import { StationDeleteDialogComponent } from './delete/station-delete-dialog.component';
import { StationRoutingModule } from './route/station-routing.module';

@NgModule({
  imports: [SharedModule, StationRoutingModule],
  declarations: [StationComponent, StationDetailComponent, StationUpdateComponent, StationDeleteDialogComponent],
})
export class StationsdbStationModule {}
