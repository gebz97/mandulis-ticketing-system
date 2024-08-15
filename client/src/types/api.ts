export type Paged<T> = {
  total: number;
  items: Array<T>;
};

export type MessageResponse = {
  message: string;
};

export type NAVIGATION_LINK = {
  name: string;
  href: string;
  icon: any;
  children?: NAVIGATION_LINK[];
};
