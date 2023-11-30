import { VehicleEnergySource, VehicleType } from "../../utils/types";

export interface UserDataRegisterInput {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  paymentMethod: string;
  nif: string;
  licensePlateNumber: string;
  vehicleType: string;
  vehicleEnergySource: string;
}

export interface UserDataLoginInput {
  email: string;
  password: string;
}

export interface UserDataAddNewVehicleInput {
  licensePlateNumber: string;
  vehicleType: string;
  vehicleEnergySource: string;
}

export interface UserPaymentMethodInput{
  paymentMethod: string;
}

export interface UserNearbyParksType {
  parkId: string;
  distanceKm: number;
  spotTypeOccupancies: SpotTypeOccupancies[];
}

export interface SpotTypeOccupancies {
  spotVehicleType: VehicleType;
  spotType: VehicleEnergySource;
  availableSpots: number;
}

export interface UserParkingHistoryOutput {
  parkingHistoryId: string;
  startTime: string;
  endTime: string;
  hoursBetweenEntranceExit: number;
  minutesBetweenEntranceExit: number;
  parkId: number;
  customerId: number;
  price: number;
}

export const SET_USER_DATA = "SET_USER_DATA";

export const POST_USER_DATA = "POST_USER_DATA";
