import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { LoginScreen } from "./login/login";
import { RegisterScreen } from "./register/register";
import { HomeScreen } from "./home/home";
import { AddVechileScreen } from "./vechile/addNewVechile";
import { CarDetailCard } from "../components/CarDetailCard/carDetailCard";
import { MapScreen } from "./map/map";

export const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginScreen />} />
        <Route path="/register" element={<RegisterScreen />} />
        <Route path="/home" element={<HomeScreen />} />
        <Route path="/addVechile" element={<AddVechileScreen />} />
        <Route path="/map" element={<MapScreen />} />
      </Routes>
    </Router>
  );
};
