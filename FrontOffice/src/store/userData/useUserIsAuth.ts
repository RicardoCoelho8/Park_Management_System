import { useSelector } from "react-redux";
import { RootState } from "../store";

export const useUserIsAuth = () => {
  return useSelector((state: RootState) => state.userData.isAuth);
};
