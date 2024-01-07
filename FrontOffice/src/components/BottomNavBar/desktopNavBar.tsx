import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../../store";
import { removeUserFromLocalStorage } from "../../utils/jwtUtils";
import { Container, Navbar } from "react-bootstrap";

export const DesktopNavBar: React.FC = () => {
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
        position: "fixed",
        width: "10%",
        color: "rgb(0, 92, 102)",
        top: "0",
        left: "0",
        display: "flex",
        flexDirection: "column",
        height: "100vh",
        justifyContent: "space-between",
      }}
    >
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
        onClick={handleOnClickLogout}
      >
        <i className="bi bi-power"></i>
        <p>Logout</p>
      </Container>
    </Navbar>
  );
};
