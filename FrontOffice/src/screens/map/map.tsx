import React, { useEffect, useState } from "react";

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
import { MapDisplay, MapMarker } from "../../components/MapDisplay/mapDisplay";
import { NavBar } from "../../components/BottomNavBar/navBar";

export const MapScreen: React.FC = () => {
  let sortedParks: UserNearbyParksType[] = [];
  const [latitude, setLatitude] = useState<number>(0);
  const [longitude, setLongitude] = useState<number>(0);
  const [mapMarkers, setMapMarkers] = useState<MapMarker[]>([]);
  const userId = useUserId();
  const { data: userVehicles } = useGetUserVehiclesQuery(userId as string);
  const { data: userNearbyParks } = useGetParksNearbyQuery(
    { latitude, longitude },
    { skip: latitude === 0 && longitude === 0 }
  );
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        console.log("Latitude is :", position.coords.latitude);
        console.log("Longitude is :", position.coords.longitude);
        setLatitude(position.coords.latitude);
        setLongitude(position.coords.longitude);
      });
    }
  }, []);

  useEffect(() => {
    if (userNearbyParks) {
      console.log("userNearbyParks", userNearbyParks);
      console.log("userVehicles", userVehicles);

      sortedParks = [...userNearbyParks].sort(
        (a, b) => (a.distanceKm || 0) - (b.distanceKm || 0)
      );
      const cards = getAvailableSpotsPerPark();
      setMapMarkers(cards);
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

    const cards: MapMarker[] = sortedParks.map((park, index) => {
      const distance = park.distanceKm?.toFixed(2);
      const parkId = park.parkId;
      const isNearestPark = index === 0;
      // Add available spots to each park not being used at the moment
      const availableSpots = userVehicles.map((userVehicle) => {
        const amount = matchAvailableSpots(
          userVehicle,
          park.spotTypeOccupancies
        );
        return {
          licensePlate: userVehicle.licensePlateNumber,
          amount: amount,
        };
      });

      return {
        latitude: park.latitude,
        longitude: park.longitude,
        popupText: isNearestPark
          ? `This is the nearest Park ${(
              <br />
            )} Park ${parkId} - ${distance} km away ${(<br />)} ${
              availableSpots[0].amount
            } available spots`
          : `Park ${parkId} - ${distance} km away ${(<br />)} ${
              availableSpots[0].amount
            } available spots`,
      };
    });

    return cards;
  };

  return (
    <>
      <Container style={{ padding: 0 }}>
        {latitude !== 0 && longitude !== 0 && (
          <MapDisplay
            markers={mapMarkers}
            center={[latitude, longitude]}
            userMarker={{
              latitude: latitude,
              longitude: longitude,
              popupText: `You are here`,
            }}
          />
        )}
      </Container>
      <NavBar />
    </>
  );
};
