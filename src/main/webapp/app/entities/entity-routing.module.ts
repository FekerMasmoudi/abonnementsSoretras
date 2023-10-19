import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'station',
        data: { pageTitle: 'gatewaydbApp.stationsdbStation.home.title' },
        loadChildren: () => import('./stationsdb/station/station.module').then(m => m.StationsdbStationModule),
      },
      {
        path: 'center',
        data: { pageTitle: 'gatewaydbApp.centredbCenter.home.title' },
        loadChildren: () => import('./centredb/center/center.module').then(m => m.CentredbCenterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
