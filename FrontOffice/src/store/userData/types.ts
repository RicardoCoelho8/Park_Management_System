export interface UserDataRegisterInput {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  paymentMethod: string;
  nif: string;
  licensePlateNumber: string;
  vehicleType: string;
}

export interface UserDataLoginInput {
  email: string;
  password: string;
}

export const SET_USER_DATA = "SET_USER_DATA";

export const POST_USER_DATA = "POST_USER_DATA";
