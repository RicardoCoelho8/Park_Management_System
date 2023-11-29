import { useSelector } from "react-redux";
import { RootState } from "../store";

export const useUserEmail = () => {
  return useSelector((state: RootState) => state.userData.email);
};
