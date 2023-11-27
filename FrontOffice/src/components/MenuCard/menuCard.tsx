import React from "react";
import { Container } from "react-bootstrap";

export interface MenuCard {
  title: string;
  subtitle: string;
  icon: string;
  onClick: () => void;
}

interface MenuCardProps {
  cards: MenuCard[];
}
export const MenuCard: React.FC<MenuCardProps> = (props) => {
  const { cards } = props;
  return (
    <>
      {cards.map((card) => {
        const isFirstItem = cards.indexOf(card) === 0;
        return (
          <Container
            style={{
              display: "flex",
              flexDirection: "row",
              borderRadius: "0.938rem",
              marginTop: isFirstItem ? "" : "0.625rem",
              background:
                "linear-gradient(180deg, rgba(0, 212, 255, 0.8) 0%, rgba(20, 100, 120, 0.6) 40%, rgba(12, 57, 80, 0.4) 80%, rgba(12, 57, 80, 0.2) 100%)",
              boxShadow:
                "rgba(0, 0, 0, 0.25) 0px 14px 28px, rgba(0, 0, 0, 0.22) 0px 10px 10px",
            }}
            onClick={card.onClick}
          >
            <Container
              style={{
                display: "flex",
                flexDirection: "column",
                color: "white",
                justifyContent: "center",
              }}
            >
              <h1 style={{ fontFamily: "'Open Sans', sans-serif" }}>
                {card.title}
              </h1>
              <h5 style={{ fontFamily: "'Open Sans', sans-serif" }}>
                {card.subtitle}
              </h5>
            </Container>
            <Container style={{ width: "40%" }}>
              <i
                className={card.icon}
                style={{
                  fontSize: "4.375em",
                  color: "white",
                  textAlign: "center",
                }}
              />
            </Container>
          </Container>
        );
      })}
    </>
  );
};
