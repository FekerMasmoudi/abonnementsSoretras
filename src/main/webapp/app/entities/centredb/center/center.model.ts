export interface ICenter {
  id: string;
  deccent?: number | null;
  delcent?: string | null;
  delcentfr?: string | null;
  deadrce?: string | null;
  deobser?: string | null;
}

export type NewCenter = Omit<ICenter, 'id'> & { id: null };
