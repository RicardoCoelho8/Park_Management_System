import { useSelector } from "react-redux";
import { RootState } from "../store";
import { getUserIdFromLocalStorage } from "../../utils/jwtUtils";

export const useUserId = (): string | null => {
  return getUserIdFromLocalStorage();
};
