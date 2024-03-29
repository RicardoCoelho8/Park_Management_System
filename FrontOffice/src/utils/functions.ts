import { VehicleEnergySource, VehicleType } from "./types";

export const validateLicensePlate = (value: any): boolean => {
  const regex =
    /(^[A-Z]{2}-\d{2}-\d{2}$)|(^\d{2}-[A-Z]{2}-\d{2}$)|(^\d{2}-\d{2}-[A-Z]{2}$)|(^[A-Z]{2}-[A-Z]{2}-\d{2}$)|(^[A-Z]{2}-\d{2}-[A-Z]{2}$)|(^\d{2}-[A-Z]{2}-[A-Z]{2}$)/;
  return regex.test(value);
};

export const getRandomVehicleType = (): VehicleType => {
  const types = Object.values(VehicleType);
  const randomIndex = Math.floor(Math.random() * types.length);
  return types[randomIndex] as VehicleType;
};
export const getRandomVehicleEnergySource = (): VehicleEnergySource => {
  const types = Object.values(VehicleEnergySource);
  const randomIndex = Math.floor(Math.random() * types.length);
  return types[randomIndex] as VehicleEnergySource;
};
