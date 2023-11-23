import React from "react";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { MenuCard } from "../../components/MenuCard/menuCard";
import { Container } from "react-bootstrap";

export const HomeScreen: React.FC = () => {
  const cards: MenuCard[] = [
    {
      title: "Vechiles",
      subtitle: "View All Your Vechiles Here",
      icon: "bi bi-car-front",
    },
    {
      title: "Payments",
      subtitle: "View All Your Transactions Here",
      icon: "bi bi-credit-card-2-back",
    },
    {
      title: "Vechiles",
      subtitle: "View All Your Vechiles Here",
      icon: "bi bi-car-front",
    },
  ];
  return (
    <>
      <Container style={{ width: "150px", height: "150px" }} />
      <Container style={{ padding: "0 0.625rem" }}>
        <MenuCard cards={cards} />
      </Container>
      <BottomNavBar />
    </>
  );
};
