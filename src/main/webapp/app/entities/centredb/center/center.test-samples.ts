import { ICenter, NewCenter } from './center.model';

export const sampleWithRequiredData: ICenter = {
  id: 'a51e6a01-110a-4623-88e5-1d5cd90b0702',
  deccent: 92523,
};

export const sampleWithPartialData: ICenter = {
  id: 'c7a31966-38ac-4b8b-b0c4-cbe0bd32344a',
  deccent: 13077,
  delcent: 'Future',
  delcentfr: 'Ergonomic wireless',
  deobser: 'alarm bandwidth Agent',
};

export const sampleWithFullData: ICenter = {
  id: '6a3d0f97-387e-4e1d-b11d-c56024b85786',
  deccent: 173,
  delcent: 'Senior IB radical',
  delcentfr: 'transmitting',
  deadrce: 'context-sensitive Account services',
  deobser: 'complexity mobile Account',
};

export const sampleWithNewData: NewCenter = {
  deccent: 33039,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
