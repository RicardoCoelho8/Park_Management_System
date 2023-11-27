import React, { useEffect, useState } from "react";
import { CarDetailCard } from "../../components/CarDetailCard/carDetailCard";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { Button, Container } from "react-bootstrap";
import {
  useAddNewVehicleMutation,
  useGetUserVehiclesQuery,
} from "../../store/userData/api";
import { useUserId } from "../../store/userData/useUserId";
import { CarBrand, VehicleType } from "../../utils/types";
import { UserDataAddNewVehicleInput } from "../../store";
import { ModalAddNewVehicle } from "../../components/ModalAddNewVehicle/modal";
import {
  getRandomVehicleEnergySource,
  getRandomVehicleType,
} from "../../utils/functions";
import { ModalErrorForm } from "../../components";

export const AddVechileScreen: React.FC = () => {
  const userId = useUserId();
  let carDetailsCard: CarDetailCard[] = [];
  const { data, refetch } = useGetUserVehiclesQuery(userId as string);
  const [showModalAddNewVehicle, setShowModalAddNewVehicle] = useState(false);
  const [showModalError, setShowModalError] = useState(false);
  const [addNewVechile, { error, data: newCarDetail }] =
    useAddNewVehicleMutation();

  useEffect(() => {
    if (error) {
      setShowModalError(true);
    } else if (newCarDetail) {
      refetch();
    }

    console.log(data);
    console.log(newCarDetail);
  }, [newCarDetail, error, data]);

  const getRandomVehicleBrand = (): CarBrand => {
    const types = Object.values(CarBrand);
    const randomIndex = Math.floor(Math.random() * types.length);
    return types[randomIndex] as CarBrand;
  };
  const getCarDetailsCard = () => {
    const cards: CarDetailCard[] = [];
    if (data) {
      data.map((car: UserDataAddNewVehicleInput) => {
        cards.push({
          licensePlate: car.licensePlateNumber,
          brand: getRandomVehicleBrand(),
          energySource: car.vehicleEnergySource,
          icon:
            car.vehicleType === VehicleType.automobile
              ? "bi bi-car-front"
              : "bi bi-bicycle",
        });
      });
    }
    return cards;
  };
  carDetailsCard = getCarDetailsCard();

  const handleOnClickAddNewVechile = () => {
    setShowModalAddNewVehicle(true);
  };

  const handleOnClickModalAddNewVechile = (licensePlateNumber: string) => {
    addNewVechile({
      newVehicle: {
        licensePlateNumber,
        vehicleEnergySource: getRandomVehicleEnergySource(),
        vehicleType: getRandomVehicleType(),
      },
      userId: userId as string,
    });
    setShowModalAddNewVehicle(false);
  };

  return (
    <>
      <Container style={{ height: "20vh", width: "100%" }} />
      <CarDetailCard carDetailCard={carDetailsCard} />
      <Container style={{ padding: "0 0.625rem" }}>
        <Button onClick={handleOnClickAddNewVechile} style={{ width: "100%" }}>
          Add New Vechile
        </Button>
      </Container>
      <ModalAddNewVehicle
        showModal={showModalAddNewVehicle}
        setShowModal={setShowModalAddNewVehicle}
        addVehicle={handleOnClickModalAddNewVechile}
      />
      <ModalErrorForm
        showModal={showModalError}
        setShowModal={setShowModalError}
      />
      <BottomNavBar />
    </>
  );
};
