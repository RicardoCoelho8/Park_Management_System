import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../../store";
import { removeUserFromLocalStorage } from "../../utils/jwtUtils";

export const BottomNavBar: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleOnClickLogout = () => {
    dispatch(logout());
    removeUserFromLocalStorage();
    navigate("/");
  };

  return (
    <Navbar
      style={{
        background:
          "linear-gradient(180deg, rgba(0, 212, 255, 0.8) 0%, rgba(20, 100, 120, 0.6) 40%, rgba(12, 57, 80, 0.4) 80%, rgba(12, 57, 80, 0.2) 100%)",
        position: "fixed",
        width: "100%",
        bottom: "0",
        borderRadius: "1.25rem 1.25rem 0 0",
        color: "white",
      }}
    >
      <Container fluid style={{ display: "flex", flexDirection: "column" }}>
        <i className="bi bi-wallet-fill"></i>
        <p>Wallet</p>
      </Container>
      <Container
        fluid
        style={{ display: "flex", flexDirection: "column" }}
        onClick={() => navigate("/home")}
      >
        <i className="bi bi-house-door-fill"></i>
        <p>Home</p>
      </Container>

      <Container
        fluid
        style={{ display: "flex", flexDirection: "column" }}
        onClick={() => navigate("/map")}
      >
        <i className="bi bi-map-fill"></i>
        <p>Map</p>
      </Container>
      <Container
        fluid
        style={{ display: "flex", flexDirection: "column" }}
        onClick={handleOnClickLogout}
      >
        <i className="bi bi-power"></i>
        <p>Logout</p>
      </Container>
    </Navbar>
  );
};
