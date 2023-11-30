import { useSelector } from "react-redux";
import { RootState } from "../store";

export const useUserName = () => {
  return useSelector((state: RootState) => state.userData.name);
};
