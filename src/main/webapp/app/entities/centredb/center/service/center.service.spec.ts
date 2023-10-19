import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICenter } from '../center.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../center.test-samples';

import { CenterService } from './center.service';

const requireRestSample: ICenter = {
  ...sampleWithRequiredData,
};

describe('Center Service', () => {
  let service: CenterService;
  let httpMock: HttpTestingController;
  let expectedResult: ICenter | ICenter[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CenterService);
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

    it('should create a Center', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const center = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(center).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Center', () => {
      const center = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(center).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Center', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Center', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Center', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCenterToCollectionIfMissing', () => {
      it('should add a Center to an empty array', () => {
        const center: ICenter = sampleWithRequiredData;
        expectedResult = service.addCenterToCollectionIfMissing([], center);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(center);
      });

      it('should not add a Center to an array that contains it', () => {
        const center: ICenter = sampleWithRequiredData;
        const centerCollection: ICenter[] = [
          {
            ...center,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCenterToCollectionIfMissing(centerCollection, center);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Center to an array that doesn't contain it", () => {
        const center: ICenter = sampleWithRequiredData;
        const centerCollection: ICenter[] = [sampleWithPartialData];
        expectedResult = service.addCenterToCollectionIfMissing(centerCollection, center);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(center);
      });

      it('should add only unique Center to an array', () => {
        const centerArray: ICenter[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const centerCollection: ICenter[] = [sampleWithRequiredData];
        expectedResult = service.addCenterToCollectionIfMissing(centerCollection, ...centerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const center: ICenter = sampleWithRequiredData;
        const center2: ICenter = sampleWithPartialData;
        expectedResult = service.addCenterToCollectionIfMissing([], center, center2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(center);
        expect(expectedResult).toContain(center2);
      });

      it('should accept null and undefined values', () => {
        const center: ICenter = sampleWithRequiredData;
        expectedResult = service.addCenterToCollectionIfMissing([], null, center, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(center);
      });

      it('should return initial array if no Center is added', () => {
        const centerCollection: ICenter[] = [sampleWithRequiredData];
        expectedResult = service.addCenterToCollectionIfMissing(centerCollection, undefined, null);
        expect(expectedResult).toEqual(centerCollection);
      });
    });

    describe('compareCenter', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCenter(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareCenter(entity1, entity2);
        const compareResult2 = service.compareCenter(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareCenter(entity1, entity2);
        const compareResult2 = service.compareCenter(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareCenter(entity1, entity2);
        const compareResult2 = service.compareCenter(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
