export interface IStation {
  id: string;
  namefr?: string | null;
  namear?: string | null;
  longitude?: string | null;
  lattitude?: string | null;
  decstat?: string | null;
  status?: string | null;
}

export type NewStation = Omit<IStation, 'id'> & { id: null };
