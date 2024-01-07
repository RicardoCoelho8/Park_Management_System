import React from "react";
import { Container } from "react-bootstrap";
import { NavBar } from "../../components/BottomNavBar/navBar";

export const PaymentScreen: React.FC = () => {
  return (
    <>
      <Container style={{ paddingTop: "40%" }}>
        <Container
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <h1>Coming soon ...</h1>
        </Container>
      </Container>
      <NavBar />
    </>
  );
};
