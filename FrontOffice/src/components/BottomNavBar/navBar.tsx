import React from "react";
import { useUserRole } from "../../store/userData/useUserRole";
import { UserRoles } from "../../store";
import { BottomNavBar } from "./bottomNavBar";
import { DesktopNavBar } from "./desktopNavBar";

export const NavBar: React.FC = () => {
  const role = useUserRole();
  const isCustomer = role === UserRoles.CUSTOMER;
  return isCustomer ? <BottomNavBar /> : <DesktopNavBar />;
};
