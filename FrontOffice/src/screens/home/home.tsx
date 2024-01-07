import React from "react";

import { useUserRole } from "../../store/userData/useUserRole";
import { UserRoles } from "../../store";
import { HomeScreenCustomer } from "./homeCustomer";
import { HomeScreenParkManager } from "./homeParkManagement";
import { HomeScreenCustomerManager } from "./homeCustomerManagement";

export const HomeScreen: React.FC = () => {
  const role = useUserRole();
  const isCustomer = role === UserRoles.CUSTOMER;
  const isParkManager = role === UserRoles.PARK_MANAGER;

  return isCustomer ? (
    <HomeScreenCustomer />
  ) : isParkManager ? (
    <HomeScreenParkManager />
  ) : (
    <HomeScreenCustomerManager />
  );
};
