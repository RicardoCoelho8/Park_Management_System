
import { getUserIdFromLocalStorage } from "../../utils/jwtUtils";

export const useUserId = (): string | null => {
  return getUserIdFromLocalStorage();
};
