import { PayloadAction, createSlice } from "@reduxjs/toolkit";

export interface UserDataState {
  userId: string;
  userRole: string;
  email: string;
  name: string;
  isAuth: boolean;
}

export const initialState: UserDataState = {
  userId: "",
  userRole: "",
  email: "",
  name: "",
  isAuth: false,
};

export const userSlice = createSlice({
  name: "userData",
  initialState,
  reducers: {
    setUserData(state, action: PayloadAction<UserDataState>) {
      state.userId = action.payload.userId;
      state.userRole = action.payload.userRole;
      state.email = action.payload.email;
      state.name = action.payload.name;
      state.isAuth = true;
    },
    logout(state) {
      state.userId = "";
      state.userRole = "";
      state.email = "";
      state.name = "";
      state.isAuth = false;
    },
  },
});

export const { setUserData, logout } = userSlice.actions;
export const userDataReducer = userSlice.reducer;
