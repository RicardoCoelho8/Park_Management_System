import { useSelector } from "react-redux";
import { RootState } from "../store";
import { getUserRoleFromLocalStorage } from "../../utils/jwtUtils";

export const useUserRole = (): string | null => {
  return getUserRoleFromLocalStorage();
};
