import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { LoginScreen } from "./login/login";
import { RegisterScreen } from "./register/register";
import { HomeScreen } from "./home/home";
import { BottomNavBar } from "../components/BottomNavBar/bottomNavBar";

export const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginScreen />} />
        <Route path="/register" element={<RegisterScreen />} />
        <Route path="/home" element={<HomeScreen />} />
      </Routes>
    </Router>
  );
};
