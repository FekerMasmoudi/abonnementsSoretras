import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStation, NewStation } from '../station.model';

export type PartialUpdateStation = Partial<IStation> & Pick<IStation, 'id'>;

export type EntityResponseType = HttpResponse<IStation>;
export type EntityArrayResponseType = HttpResponse<IStation[]>;

@Injectable({ providedIn: 'root' })
export class StationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stations', 'stationsdb');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(station: NewStation): Observable<EntityResponseType> {
    return this.http.post<IStation>(this.resourceUrl, station, { observe: 'response' });
  }

  update(station: IStation): Observable<EntityResponseType> {
    return this.http.put<IStation>(`${this.resourceUrl}/${this.getStationIdentifier(station)}`, station, { observe: 'response' });
  }

  partialUpdate(station: PartialUpdateStation): Observable<EntityResponseType> {
    return this.http.patch<IStation>(`${this.resourceUrl}/${this.getStationIdentifier(station)}`, station, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IStation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStationIdentifier(station: Pick<IStation, 'id'>): string {
    return station.id;
  }

  compareStation(o1: Pick<IStation, 'id'> | null, o2: Pick<IStation, 'id'> | null): boolean {
    return o1 && o2 ? this.getStationIdentifier(o1) === this.getStationIdentifier(o2) : o1 === o2;
  }

  addStationToCollectionIfMissing<Type extends Pick<IStation, 'id'>>(
    stationCollection: Type[],
    ...stationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stations: Type[] = stationsToCheck.filter(isPresent);
    if (stations.length > 0) {
      const stationCollectionIdentifiers = stationCollection.map(stationItem => this.getStationIdentifier(stationItem)!);
      const stationsToAdd = stations.filter(stationItem => {
        const stationIdentifier = this.getStationIdentifier(stationItem);
        if (stationCollectionIdentifiers.includes(stationIdentifier)) {
          return false;
        }
        stationCollectionIdentifiers.push(stationIdentifier);
        return true;
      });
      return [...stationsToAdd, ...stationCollection];
    }
    return stationCollection;
  }
}
