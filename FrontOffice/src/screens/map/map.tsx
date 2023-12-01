import React, { useEffect, useState } from "react";
import { MapLocationCard } from "../../components/MapLocationCard/mapLocationCard";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { Container } from "react-bootstrap";
import { useUserId } from "../../store/userData/useUserId";
import {
  useGetParksNearbyQuery,
  useGetUserVehiclesQuery,
} from "../../store/userData/api";
import {
  SpotTypeOccupancies,
  UserDataAddNewVehicleInput,
  UserNearbyParksType,
} from "../../store";

export const MapScreen: React.FC = () => {
  const userId = useUserId();
  const { data: userVehicles } = useGetUserVehiclesQuery(userId as string);
  const { data: userNearbyParks } = useGetParksNearbyQuery();
  let sortedParks: UserNearbyParksType[] = [];
  const [mapLocationCards, setMapLocationCards] = useState<MapLocationCard[]>([]);

  useEffect(() => {
    if (userNearbyParks) {
      console.log("userNearbyParks", userNearbyParks);
      console.log("userVehicles", userVehicles);

      sortedParks = [...userNearbyParks].sort((a, b) => (a.distanceKm || 0) - (b.distanceKm || 0));
      const cards = getAvailableSpotsPerPark();
      setMapLocationCards(cards);
    }
  }, [userVehicles, userNearbyParks]);

  const matchAvailableSpots = (
    userVehicle: UserDataAddNewVehicleInput,
    spotTypeOccupancies: SpotTypeOccupancies[]
  ) => {
    let availableSpots = 0;
    spotTypeOccupancies.map((spotTypeOccupancy) => {
      if (
        spotTypeOccupancy.spotVehicleType === userVehicle.vehicleType &&
        spotTypeOccupancy.spotType === userVehicle.vehicleEnergySource
      ) {
        availableSpots = spotTypeOccupancy.availableSpots;
      }
    });
    return availableSpots;
  };

  const getAvailableSpotsPerPark = () => {
    if (!userVehicles) {
      return [];
    }

    const cards: MapLocationCard[] = sortedParks.map((park) => {
      const distance = park.distanceKm?.toFixed(2);
      const parkId = park.parkId;

      const availableSpots = userVehicles.map((userVehicle) => {
        const amount = matchAvailableSpots(userVehicle, park.spotTypeOccupancies);
        return {
          licensePlate: userVehicle.licensePlateNumber,
          amount: amount,
        };
      });

      return {
        parkId: parkId,
        distance: distance as string,
        availableSpots: availableSpots,
      };
    });

    return cards;
  };

  return (
    <>
      <Container style={{ width: "6.25rem", height: "6.25rem" }} />
      {mapLocationCards.length > 0 && (
        <MapLocationCard cards={mapLocationCards} />
      )}
      <BottomNavBar />
    </>
  );
};
