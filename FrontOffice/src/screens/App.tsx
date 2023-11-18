import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { LoginScreen } from "./login/login";
import { RegisterScreen } from "./register/register";
import { Button, Container, Form, Image } from "react-bootstrap";
import { logo } from "../images";

export const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginScreen />} />
        <Route path="/register" element={<RegisterScreen />} />
      </Routes>
    </Router>
  );
};
