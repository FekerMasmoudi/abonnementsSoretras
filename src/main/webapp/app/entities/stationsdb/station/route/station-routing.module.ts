import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StationComponent } from '../list/station.component';
import { StationDetailComponent } from '../detail/station-detail.component';
import { StationUpdateComponent } from '../update/station-update.component';
import { StationRoutingResolveService } from './station-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const stationRoute: Routes = [
  {
    path: '',
    component: StationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StationDetailComponent,
    resolve: {
      station: StationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StationUpdateComponent,
    resolve: {
      station: StationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StationUpdateComponent,
    resolve: {
      station: StationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stationRoute)],
  exports: [RouterModule],
})
export class StationRoutingModule {}
