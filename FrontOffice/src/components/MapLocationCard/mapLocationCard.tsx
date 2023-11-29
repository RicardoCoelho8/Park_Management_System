import React, { useEffect } from "react";
import { Container } from "react-bootstrap";

export interface MapLocationCard {
  parkId: string;
  distance: string;
  availableSpots: {
    licensePlate: string;
    amount: number;
  }[];
}

export interface MapLocationCardProps {
  cards: MapLocationCard[];
}
export const MapLocationCard: React.FC<MapLocationCardProps> = (props) => {
  const { cards } = props;
  useEffect(() => {
    console.log("CARDS IN CARDS", cards);
  }, [cards]);
  return (
    <Container
      className="scroll-container"
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "0.625rem",
        overflowY: "auto",
        height: "55vh",
      }}
    >
      {cards.map((card) => {
        return (
          <Container
            style={{
              display: "flex",
              flexDirection: "row",
              background:
                "linear-gradient(180deg, rgba(0, 212, 255, 0.8) 0%, rgba(20, 100, 120, 0.6) 40%, rgba(12, 57, 80, 0.4) 80%, rgba(12, 57, 80, 0.2) 100%)",
              boxShadow:
                "rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset",
              color: "white",
              borderRadius: "1rem",
            }}
          >
            <Container
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <i
                className="bi bi-pin-map-fill"
                style={{ fontSize: "3.125rem" }}
              />
            </Container>
            <Container style={{ display: "flex", flexDirection: "column" }}>
              <p>{`Park ${card.parkId}`}</p>
              <p>{`${card.distance} km away`}</p>
              {card.availableSpots.map((spot) => {
                return (
                  <p>
                    {`${spot.amount} spot(s) available for ${spot.licensePlate}`}
                  </p>
                );
              })}

              <></>
            </Container>
          </Container>
        );
      })}
    </Container>
  );
};
