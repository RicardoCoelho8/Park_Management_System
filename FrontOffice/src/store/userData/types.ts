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

export interface UserPaymentMethodInput {
  paymentMethod: string;
}

export interface SpotOperationalInput {
  parkNumber: string;
  spotNumber: string;
  operational: boolean;
  managerName: string;
}

export interface SpotsDetailsInput {
  spotNumber: string;
  spotType: string;
  spotVehicleType: string;
  floorLevel: string;
  operational: boolean;
}

export interface ParkOutput {
  parkNumber: string;
}

export interface SpotOutput {
  spotNumber: string;
  spotType: string;
  spotVehicleType: string;
  floorLevel: string;
  occupied: boolean;
  operational: boolean;
}

export interface ThresholdsOutput {
  parkiesPerHour: number;
  parkiesPerMinute: number;
}

export interface UserNearbyParksType {
  parkId: string;
  distanceKm: number;
  spotTypeOccupancies: SpotTypeOccupancies[];
  latitude: number;
  longitude: number;
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
  parkId: string;
  periodStart: string;
  periodEnd: string;
  thresholds: ThresholdCostOutput[];
}

export interface OvernightConfig {
  overnightFee: number;
  enabled: boolean;
}

export interface OvernightEnableInput {
  parkNumber: string;
  status: boolean;
}

export interface OvernightFeeInput {
  parkNumber: string;
  price: number;
}

export interface ThresholdCostOutput {
  thresholdMinutes: string;
  costPerMinuteAutomobiles: string;
  costPerMinuteMotorcycles: string;
}

export interface PriceTableEntry {
  parkId: string;
  periodStart: string;
  periodEnd: string;
  thresholds: ThresholdCost[];
}

export interface ThresholdCost {
  thresholdMinutes: string;
  costPerMinuteAutomobiles: string;
  costPerMinuteMotorcycles: string;
}

export interface AddParkiesToUserInput {
  userIds: string[];
  amount: number;
  reason: string;
}

export interface GetAllUsersOutputItem {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  nif: number;
  parkingHistory: [];
  totalParkies: number;
  vehicles: [
    {
      licensePlateNumber: string;
      vehicleType: string;
      vehicleEnergySource: string;
    }
  ];
  role: string;
  paymentMethod: string;
  userStatus: string;
}

export interface AssignParkyCoinsInput {
  userIds: number[];
  amount: number;
  reason: "";
}
export interface ParkyEvent {
  eventId: number;
  amount: number;
  reason: string;
  managerId: number;
  transactionTime: string;
}

export interface UserParkyWalletOutput {
  userId: number;
  parkies: number;
  parkyEvents: ParkyEvent[];
}
export interface UserParkyCoinsWalletFlag {
  customerID: string;
  parkyFlag: boolean;
}

export interface ParkReportOutput {
  percentageCar: number;
  percentageMotorcycle: number;
  percentageFuel: number;
  percentageGPL: number;
  percentageElectric: number;
  totalVehicles: number;
}
export enum UserRoles {
  PARK_MANAGER = "PARK_MANAGER",
  CUSTOMER = "CUSTOMER",
  CUSTOMER_MANAGER = "CUSTOMER_MANAGER",
}
