import React from "react";
import { NavBar } from "../../components/BottomNavBar/navBar";
import { useUserName } from "../../store/userData/useUserName";
import { Container } from "react-bootstrap";
import { ManageParkScreen } from "../managePark/managePark";

export const HomeScreenParkManager: React.FC = () => {
  const userName = useUserName();
  return (
    <>
      <Container
        style={{
          width: "100%",
          display: "flex",
          alignItems: "center",
          height: "17vh",
          padding: "0 0 0 1.875rem",
          marginLeft: "15vh",
        }}
      >
        <i
          className="bi bi-person-circle"
          style={{ fontSize: "1.875rem", color: "#005c66" }}
        ></i>
        <Container
          style={{
            display: "flex",
            flexDirection: "column",
            color: "#005c66",
            padding: "1.875rem 0 0 0.625rem ",
          }}
        >
          <h5>Welcome back </h5>
          <h3>{userName}</h3>
        </Container>
      </Container>
      <ManageParkScreen />
      <NavBar />
    </>
  );
};
