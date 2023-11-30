import React from "react";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { Container } from "react-bootstrap";

export const PaymentScreen: React.FC = () => {
  return (
    <>
      <Container style={{ paddingTop: "40%" }}>
        <Container style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
          <h1>Coming soon ...</h1>
        </Container>
      </Container>
      <BottomNavBar />
    </>
  );
};
