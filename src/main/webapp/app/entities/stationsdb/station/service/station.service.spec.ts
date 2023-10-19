import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStation } from '../station.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../station.test-samples';

import { StationService } from './station.service';

const requireRestSample: IStation = {
  ...sampleWithRequiredData,
};

describe('Station Service', () => {
  let service: StationService;
  let httpMock: HttpTestingController;
  let expectedResult: IStation | IStation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Station', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const station = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(station).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Station', () => {
      const station = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(station).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Station', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Station', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Station', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStationToCollectionIfMissing', () => {
      it('should add a Station to an empty array', () => {
        const station: IStation = sampleWithRequiredData;
        expectedResult = service.addStationToCollectionIfMissing([], station);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(station);
      });

      it('should not add a Station to an array that contains it', () => {
        const station: IStation = sampleWithRequiredData;
        const stationCollection: IStation[] = [
          {
            ...station,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStationToCollectionIfMissing(stationCollection, station);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Station to an array that doesn't contain it", () => {
        const station: IStation = sampleWithRequiredData;
        const stationCollection: IStation[] = [sampleWithPartialData];
        expectedResult = service.addStationToCollectionIfMissing(stationCollection, station);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(station);
      });

      it('should add only unique Station to an array', () => {
        const stationArray: IStation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const stationCollection: IStation[] = [sampleWithRequiredData];
        expectedResult = service.addStationToCollectionIfMissing(stationCollection, ...stationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const station: IStation = sampleWithRequiredData;
        const station2: IStation = sampleWithPartialData;
        expectedResult = service.addStationToCollectionIfMissing([], station, station2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(station);
        expect(expectedResult).toContain(station2);
      });

      it('should accept null and undefined values', () => {
        const station: IStation = sampleWithRequiredData;
        expectedResult = service.addStationToCollectionIfMissing([], null, station, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(station);
      });

      it('should return initial array if no Station is added', () => {
        const stationCollection: IStation[] = [sampleWithRequiredData];
        expectedResult = service.addStationToCollectionIfMissing(stationCollection, undefined, null);
        expect(expectedResult).toEqual(stationCollection);
      });
    });

    describe('compareStation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareStation(entity1, entity2);
        const compareResult2 = service.compareStation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareStation(entity1, entity2);
        const compareResult2 = service.compareStation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareStation(entity1, entity2);
        const compareResult2 = service.compareStation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
