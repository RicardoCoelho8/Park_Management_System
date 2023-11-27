import React from "react";
import { Container } from "react-bootstrap";
import "./carDetailCard.css";

export interface CarDetailCard {
  licensePlate: string;
  brand: string;
  energySource: string;
  icon: string;
}

interface CarDetailCardProps {
  carDetailCard: CarDetailCard[];
}

export const CarDetailCard: React.FC<CarDetailCardProps> = (props) => {
  const { carDetailCard } = props;
  return (
    <Container
      className="scroll-container"
      style={{
        WebkitOverflowScrolling: "touch",
        overflowX: "auto",
        display: "flex",
        flexDirection: "row",
        whiteSpace: "nowrap",
        padding: "0 0 0.625rem 0.625rem",
      }}
    >
      {carDetailCard.map((card) => {
        const isOnlyOneItem = carDetailCard.length === 1;
        return (
          <Container
            style={{
              background:
                "linear-gradient(180deg, rgba(0, 212, 255, 0.8) 0%, rgba(20, 100, 120, 0.6) 40%, rgba(12, 57, 80, 0.4) 80%, rgba(12, 57, 80, 0.2) 100%)",
              display: "flex",
              boxShadow:
                "rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset",
              flexDirection: "column",
              alignItems: "center",
              color: "white",
              borderRadius: "1rem",
              width: "100%",
              marginRight: isOnlyOneItem ? "" : "0.625rem",
            }}
          >
            <i className={card.icon} style={{ fontSize: "3.125rem" }} />
            <h5>License Plate</h5>
            <h2>{card.licensePlate}</h2>
            <h5>Brand</h5>
            <h2>{card.brand}</h2>
            <h5>Energy Source</h5>
            <h2>{card.energySource}</h2>
          </Container>
        );
      })}
    </Container>
  );
};
