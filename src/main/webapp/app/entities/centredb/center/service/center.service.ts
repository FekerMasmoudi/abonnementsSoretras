import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICenter, NewCenter } from '../center.model';

export type PartialUpdateCenter = Partial<ICenter> & Pick<ICenter, 'id'>;

export type EntityResponseType = HttpResponse<ICenter>;
export type EntityArrayResponseType = HttpResponse<ICenter[]>;

@Injectable({ providedIn: 'root' })
export class CenterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/centers', 'centredb');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(center: NewCenter): Observable<EntityResponseType> {
    return this.http.post<ICenter>(this.resourceUrl, center, { observe: 'response' });
  }

  update(center: ICenter): Observable<EntityResponseType> {
    return this.http.put<ICenter>(`${this.resourceUrl}/${this.getCenterIdentifier(center)}`, center, { observe: 'response' });
  }

  partialUpdate(center: PartialUpdateCenter): Observable<EntityResponseType> {
    return this.http.patch<ICenter>(`${this.resourceUrl}/${this.getCenterIdentifier(center)}`, center, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICenter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCenterIdentifier(center: Pick<ICenter, 'id'>): string {
    return center.id;
  }

  compareCenter(o1: Pick<ICenter, 'id'> | null, o2: Pick<ICenter, 'id'> | null): boolean {
    return o1 && o2 ? this.getCenterIdentifier(o1) === this.getCenterIdentifier(o2) : o1 === o2;
  }

  addCenterToCollectionIfMissing<Type extends Pick<ICenter, 'id'>>(
    centerCollection: Type[],
    ...centersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centers: Type[] = centersToCheck.filter(isPresent);
    if (centers.length > 0) {
      const centerCollectionIdentifiers = centerCollection.map(centerItem => this.getCenterIdentifier(centerItem)!);
      const centersToAdd = centers.filter(centerItem => {
        const centerIdentifier = this.getCenterIdentifier(centerItem);
        if (centerCollectionIdentifiers.includes(centerIdentifier)) {
          return false;
        }
        centerCollectionIdentifiers.push(centerIdentifier);
        return true;
      });
      return [...centersToAdd, ...centerCollection];
    }
    return centerCollection;
  }
}
