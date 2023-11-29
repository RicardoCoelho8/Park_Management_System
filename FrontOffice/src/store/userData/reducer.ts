import { PayloadAction, createSlice } from "@reduxjs/toolkit";

export interface UserDataState {
  userId: string;
  userRole: string;
  email: string;
}

export const initialState: UserDataState = {
  userId: "",
  userRole: "",
  email: "",
};

export const userSlice = createSlice({
  name: "userData",
  initialState,
  reducers: {
    setUserData(state, action: PayloadAction<UserDataState>) {
      state.userId = action.payload.userId;
      state.userRole = action.payload.userRole;
      state.email = action.payload.email;
    },
    logout(state) {
      state.userId = "";
      state.userRole = "";
      state.email = "";
    },
  },
});

export const { setUserData, logout } = userSlice.actions;
export const userDataReducer = userSlice.reducer;
