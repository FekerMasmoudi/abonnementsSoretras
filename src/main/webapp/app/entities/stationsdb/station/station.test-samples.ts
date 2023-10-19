import { IStation, NewStation } from './station.model';

export const sampleWithRequiredData: IStation = {
  id: '8a6402a8-0200-4eb5-b547-750fb48441db',
  namefr: 'Bermuda Buckinghamshire Jewelery',
  namear: 'Consultant',
  decstat: 'Intranet Account',
  status: 'Wooden',
};

export const sampleWithPartialData: IStation = {
  id: 'c1115bf5-a5f5-4e50-b19a-43410f892ea9',
  namefr: 'optimizing Strategist Silver',
  namear: 'holistic Plaza silver',
  longitude: 'Optimization Bolivia tan',
  lattitude: 'Awesome Account',
  decstat: 'payment Direct',
  status: 'Beauty deposit',
};

export const sampleWithFullData: IStation = {
  id: '43c9c79f-bb70-4bbf-be29-065205f191a2',
  namefr: 'Cotton',
  namear: 'high-level Cross-group',
  longitude: 'Naira Metal infomediaries',
  lattitude: 'transmit',
  decstat: 'multi-byte JSON',
  status: 'Customer schemas International',
};

export const sampleWithNewData: NewStation = {
  namefr: 'mission-critical reintermediate Account',
  namear: 'Sleek Dinar Maine',
  decstat: 'Bedfordshire override',
  status: 'Dollar heuristic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
