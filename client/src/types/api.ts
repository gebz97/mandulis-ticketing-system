export type Paged<T> = {
  total: number;
  items: Array<T>;
};

export type MessageResponse = {
  message: string;
};
