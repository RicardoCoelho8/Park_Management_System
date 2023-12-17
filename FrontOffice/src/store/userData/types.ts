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

export interface SpotOperationalInput{
  parkNumber: string;
  spotNumber: string;
  operational: boolean;
  managerName: string;
}

export interface SpotsDetailsInput{
  spotNumber: string;
  spotType: string;
  spotVehicleType: string;
  floorLevel: string;
  operational: boolean;
}

export interface ParkOutput{
  parkNumber: string;
}

export interface SpotOutput{
  spotNumber: string;
  spotType: string;
  spotVehicleType: string;
  floorLevel: string;
  occupied: boolean;
  operational: boolean;
}

export interface ThresholdsOutput{
  parkiesPerHour: number;
  parkiesPerMinute: number;
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

export interface PriceTableEntryOutput {
  parkId: string ;
  periodStart: string ;
  periodEnd: string ;
  thresholds: ThresholdCostOutput[] ;
}

export interface ThresholdCostOutput {
  thresholdMinutes: string ;
  costPerMinuteAutomobiles: string ;
  costPerMinuteMotorcycles: string ;
}


export interface PriceTableEntry {
  parkId: string ;
  periodStart: string ;
  periodEnd: string ;
  thresholds: ThresholdCost[] ;
}

export interface ThresholdCost {
  thresholdMinutes: string ;
  costPerMinuteAutomobiles: string ;
  costPerMinuteMotorcycles: string ;
}

export const SET_USER_DATA = "SET_USER_DATA";

export const POST_USER_DATA = "POST_USER_DATA";
