import { PayloadAction, createSlice } from "@reduxjs/toolkit";

export interface UserDataState {
  userId: string;
  userRole: string;
}

export const initialState: UserDataState = {
  userId: "",
  userRole: "",
};

export const userSlice = createSlice({
  name: "userData",
  initialState,
  reducers: {
    setUserData(state, action: PayloadAction<UserDataState>) {
      state.userId = action.payload.userId;
      state.userRole = action.payload.userRole;
    },
    logout(state) {
      state.userId = "";
      state.userRole = "";
    },
  },
});

export const { setUserData, logout } = userSlice.actions;
export const userDataReducer = userSlice.reducer;
