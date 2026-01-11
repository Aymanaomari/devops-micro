export interface Order {
  id?: string;
  orderNumber?: string;
  price: number;
  quantity: number;
  userDetails: UserDetails;
  productId: string;
  productName: string;
}

export interface UserDetails {
  email: string;
  firstName: string;
  lastName: string;
}
