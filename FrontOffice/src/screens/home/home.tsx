import React from "react";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { MenuCard } from "../../components/MenuCard/menuCard";
import { Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

export const HomeScreen: React.FC = () => {
  const navigate = useNavigate();
  const cards: MenuCard[] = [
    {
      title: "Vechiles",
      subtitle: "View All Your Vechiles Here",
      icon: "bi bi-car-front",
      onClick: () => navigate("/addVechile"),
    },
    {
      title: "Payments",
      subtitle: "View All Your Transactions Here",
      icon: "bi bi-credit-card-2-back",
      onClick: () => navigate("/addVechile"),
    },
    {
      title: "Vechiles",
      subtitle: "View All Your Vechiles Here",
      icon: "bi bi-car-front",
      onClick: () => navigate("/addVechile"),
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
